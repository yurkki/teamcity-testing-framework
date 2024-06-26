cd ..

teamcity_tests_directory=$(pwd)
workdir="teamcity_tests_infrastructure"
teamcity_server_workdir="teamcity_server"
teamcity_agent_workdir="teamcity_agent"
selenoid_workdir="selenoid"
teamcity_server_container_name="teamcity_server_instance"
teamcity_agent_container_name="teamcity_agent_instance"
selenoid_container_name="selenoid_instance"
selenoid_ui_container_name="selenoid_ui_instance"

workdir_names=($teamcity_server_workdir $teamcity_agent_workdir $selenoid_workdir)
container_names=($teamcity_server_container_name $teamcity_agent_container_name $selenoid_container_name $selenoid_ui_container_name)

################################
echo "Request IP"
export ips=$(ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1')
export ip=${ips%%$'\n'*}

echo "Current IP: $ip"

################################
echo "Delete previous run data"

sudo rm -rf $workdir
mkdir $workdir
cd $workdir

for dir in "${workdir_names[@]}"; do
  mkdir $dir
done

for container in "${container_names[@]}"; do
  docker stop $container
  docker rm $container
done

################################
echo "Start TeamCity server"
cd $teamcity_server_workdir

docker run -d -u 0 --name $teamcity_server_container_name -v $(pwd)/logs:/opt/teamcity/logs -p 8111:8111 jetbrains/teamcity-server
#-v $(pwd)/datadir:/data/teamcity_server/datadir

echo "TeamCity Server is running..."

################################
echo "Start selenoid"

cd .. && cd $selenoid_workdir
mkdir config
cp $teamcity_tests_directory/infra/browsers.json config/

docker run -d -u 0 --name $selenoid_container_name -p 4444:4444 -v /var/run/docker.sock:/var/run/docker.sock -v $(pwd)/config/:/etc/selenoid/:ro aerokube/selenoid:latest-release

image_names=($(awk -F'"' '/"image": "/{print $4}' "$(pwd)/config/browsers.json"))

echo "Pull all browser images: $image_names"

for image in "${image_names[@]}"; do
  docker pull $image
done

################################
echo "Start Selenoid UI"
docker run -d -u 0 --name $selenoid_ui_container_name -p 8080:8080 aerokube/selenoid-ui:latest-release --selenoid-uri "http://$ip:4444"

################################
echo "Setup TeamCity server"

cd .. && cd ..
mvn clean test -Dtest=SetupTest#startUpTest

################################
echo "Parse superuser token"
#sudo chmod 644 $teamcity_tests_directory/$workdir/$teamcity_server_workdir/logs/teamcity-server.log
superuser_token=$(sudo grep -o 'Super user authentication token: [0-9]*' $teamcity_tests_directory/$workdir/$teamcity_server_workdir/logs/teamcity-server.log | awk '{print $NF}')
echo "Super user token: $superuser_token"


################################
echo "Run system tests"

config=$teamcity_tests_directory/src/test/resources/config.properties

echo "host=http://$ip:8111" > $config
echo "superUserToken=$superuser_token" >> $config
echo "remote=http://localhost:4444/wd/hub" >> $config
echo "browser=firefox" >> $config
cat $config

echo "Current directory: $(pwd)"
echo "Run API tests"
mvn test -DsuiteXmlFile=testng-suites/api-suite.xml

echo "Run UI tests"
mvn test -DsuiteXmlFile=testng-suites/ui-suite.xml

read -p "Press any key to continue"
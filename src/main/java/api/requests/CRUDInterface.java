package api.requests;

public interface CRUDInterface {

    Object create(Object obj);
    Object get(String id);
    Object update(Object obj);
    Object delete(String id);
}

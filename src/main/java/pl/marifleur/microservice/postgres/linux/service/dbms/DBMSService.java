package pl.marifleur.microservice.postgres.linux.service.dbms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.marifleur.microservice.postgres.linux.manager.dbms.DBMSManager;

@Service
public class DBMSService {

    private final DBMSManager dbmsManager;

    @Autowired
    public DBMSService(DBMSManager dbmsManager) {
        this.dbmsManager = dbmsManager;
    }

    public ResponseEntity<Object> get() {
        Object outputObject = dbmsManager.getDbmsDTO();
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(outputObject, HttpStatusCode.valueOf(200));
        return responseEntity;
    }
}

package smirnov.bn.service_1.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import smirnov.bn.service_1.model.LingVarInfo;
import smirnov.bn.service_1.service.LingVarService;

@RestController
@RequestMapping("/ling_var_dict")
public class LingVarController {

    @Autowired
    private LingVarService lingVarService;

    private static final Logger logger = LoggerFactory.getLogger(LingVarController.class);

    //curl -X POST --data {\"lingVarName\":\"Tester\",\"lingVarTermLowVal\":\"2\",\"lingVarTermMedVal\":\"4\",\"lingVarTermHighVal\":\"6\"} http://localhost:8191/ling_var_dict/create-ling_var --header "Content-Type:application/json"
//curl -X POST --data {\"lingVarName\":\"Tester2\",\"lingVarTermLowVal\":\"3\",\"lingVarTermMedVal\":\"5\",\"lingVarTermHighVal\":\"8\"} http://localhost:8191/ling_var_dict/create-ling_var --header "Content-Type:application/json"
//curl -X POST --data {\"lingVarName\":\"Tester3\",\"lingVarTermLowVal\":\"1\",\"lingVarTermMedVal\":\"3\",\"lingVarTermHighVal\":\"7\"} http://localhost:8191/ling_var_dict/create-ling_var --header "Content-Type:application/json"
//curl -X POST --data {\"lingVarName\":\"Tester4\",\"lingVarTermLowVal\":\"0\",\"lingVarTermMedVal\":\"2\",\"lingVarTermHighVal\":\"5\"} http://localhost:8191/ling_var_dict/create-ling_var --header "Content-Type:application/json"
//curl -X POST --data {\"lingVarName\":\"Tester5\",\"lingVarTermLowVal\":\"0\",\"lingVarTermMedVal\":\"3\",\"lingVarTermHighVal\":\"6\"} http://localhost:8191/ling_var_dict/create-ling_var --header "Content-Type:application/json"
    //curl -X POST --data {\"lingVarName\":\"Tester\",\"lingVarTermLowVal\":\"2\",\"lingVarTermMedVal\":\"4\",\"lingVarTermHighVal\":\"6\"} //...
    //http://localhost:8191/ling_var_dict/create-ling_var //...
    //--header "Content-Type:application/json"
    @PostMapping("/create-ling_var")
    public ResponseEntity<String> createLingVar(@RequestBody LingVarInfo lingVarInfo) {
        try {
            Integer newlingVarId = lingVarService.createLingVar(lingVarInfo);
            logger.info("createLingVar() - CREATING" + "\n" + "id param: " + String.valueOf(newlingVarId));
            return new ResponseEntity<>(newlingVarId.toString(), HttpStatus.CREATED);
        }
        catch (NullPointerException e) {
            logger.error
                    ("Null Pointer Exception Error in createLingVar(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
          catch (Exception e) {
            logger.error
                    ("Error in createLingVar(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/read-{id}")
    public ResponseEntity<LingVarInfo> findLingVarById(@PathVariable Integer id) {
        try {
            logger.info("findLingVarById() - START" + "\n" + "id param: " + String.valueOf(id));
            return new ResponseEntity<>(lingVarService.findLingVarById(id), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in findLingVarById(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/read-all")
    public ResponseEntity<List<LingVarInfo>> findAllLingVars() {
        try {
            logger.info("findAllLingVars() - START");
            List<LingVarInfo> lingVarsInfo = lingVarService.findAllLingVars();

            if (lingVarsInfo != null && lingVarsInfo.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(lingVarsInfo, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error in findAllLingVars(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //https://github.com/umeshawasthi/javadevjournal/tree/master/spring/rest-pagination/src/main/java/com/javadevjournal/rest
    //https://www.javadevjournal.com/spring/rest-pagination-in-spring/
    //http://www.appsdeveloperblog.com/rest-pagination-tutorial-with-spring-mvc/
    //https://www.baeldung.com/rest-api-pagination-in-spring
    // [ http://localhost:8083/ling_var_dict/all-paginated?page=1&sizeLimit=1 ] (:)
    @RequestMapping(value = "/read-all-paginated", params = {"page", "sizeLimit"}, method = GET)
    @ResponseBody
    public ResponseEntity<List<LingVarInfo>> findAllLingVarPaginated(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "sizeLimit", defaultValue = "100") int sizeLimit) {
        try {
            logger.info("findAllLingVarPaginated() - START" + "\n" + "page param: " + String.valueOf(page) + "\n" +
                    "sizeLimit param: " + String.valueOf(sizeLimit));
            List<LingVarInfo> lingVarsInfo = lingVarService.findAllLingVarPaginated(page, sizeLimit);

            if (lingVarsInfo != null && lingVarsInfo.isEmpty()) {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {

                return new ResponseEntity<>(lingVarsInfo, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error in findAllLingVarPaginated(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //curl -X PUT --data {\"lingVarName\":\"Tester2\",\"lingVarTermLowVal\":\"3\",\"lingVarTermMedVal\":\"5\",\"lingVarTermHighVal\":\"8\"} http://localhost:8191/ling_var_dict/update-ling_var
    @PutMapping("/update-ling_var")
    public ResponseEntity<String> updateLingVar(@RequestBody LingVarInfo lingVarInfo) {
        try {
            logger.info("updateLingVar() - START" + "\n" + "id param: " + String.valueOf(lingVarInfo.getLingVarId()));
            lingVarService.updateLingVar(lingVarInfo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in updateLingVar(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //curl -X DELETE http://localhost:8191/ling_var_dict/delete-{lingVarId}
    @DeleteMapping("/delete-{lingVarId}")
    public ResponseEntity<String> deleteLingVarById(@PathVariable Integer lingVarId) {
        try {
            logger.info("deleteLingVarById() - START" + "\n" + "id param: " + String.valueOf(lingVarId));
            lingVarService.deleteLingVarById(lingVarId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in deleteLingVarById(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}

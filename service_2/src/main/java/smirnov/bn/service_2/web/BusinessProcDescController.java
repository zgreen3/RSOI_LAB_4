package smirnov.bn.service_2.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import java.util.List;

import smirnov.bn.service_2.model.BusinessProcDescInfo;
import smirnov.bn.service_2.service.BusinessProcDescService;


@RestController
@RequestMapping("/biz_proc_desc")
public class BusinessProcDescController {

    @Autowired
    private BusinessProcDescService businessProcDescService;

    private static final Logger logger = LoggerFactory.getLogger(BusinessProcDescController.class);

//curl -X POST --data "{\"bizProcName\":\"BizProc_1\",\"bizProcDescStr\":\"This is a business process 1\"}" http://localhost:8192/biz_proc_desc/create-biz_proc_desc --header "Content-Type:application/json"
//curl -X POST --data "{\"bizProcName\":\"BizProc_2\",\"bizProcDescStr\":\"This is a business process 2\"}" http://localhost:8192/biz_proc_desc/create-biz_proc_desc --header "Content-Type:application/json"
//curl -X POST --data "{\"bizProcName\":\"BizProc_3\",\"bizProcDescStr\":\"This is a business process 3\"}" http://localhost:8192/biz_proc_desc/create-biz_proc_desc --header "Content-Type:application/json"
//curl -X POST --data "{\"bizProcName\":\"BizProc_4\",\"bizProcDescStr\":\"This is a business process 4\"}" http://localhost:8192/biz_proc_desc/create-biz_proc_desc --header "Content-Type:application/json"
    @PostMapping("/create-biz_proc_desc")
    public ResponseEntity<String> createBusinessProcDesc(@RequestBody BusinessProcDescInfo businessProcDescInfo) {
        try {
            Integer newBusinessProcDescId = businessProcDescService.createBusinessProcDesc(businessProcDescInfo);
            logger.info("createBusinessProcDesc() - CREATING" + "\n" + "id param: " + String.valueOf(newBusinessProcDescId));
            return new ResponseEntity<>(newBusinessProcDescId.toString(), HttpStatus.CREATED);
        } catch (NullPointerException e) {
            logger.error
                    ("Null Pointer Exception Error in createBusinessProcDesc(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error
                    ("Error in createBusinessProcDesc(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/read-{id}")
    public ResponseEntity<BusinessProcDescInfo> findBusinessProcDescById(@PathVariable Integer id) {
        try {
            logger.info("findBusinessProcDescById() - START" + "\n" + "id param: " + String.valueOf(id));
            return new ResponseEntity<>(businessProcDescService.findBusinessProcDescById(id), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in findBusinessProcDescById(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/read-by-emp-uuid-{employeeUuid}")
    public ResponseEntity<List<BusinessProcDescInfo>> findBusinessProcDescByEmployeeUuid(@PathVariable String employeeUuid) {
        try {
            logger.info("findBusinessProcDescByEmployeeUuid() - START" + "\n" + "employeeUuid param: " + employeeUuid);
            return new ResponseEntity<>(businessProcDescService.findBusinessProcDescByEmployeeUuid(employeeUuid), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in findBusinessProcDescByEmployeeUuid(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/read-all")
    public ResponseEntity<List<BusinessProcDescInfo>> findAllBusinessProcDescs() {
        try {
            logger.info("findAllBusinessProcDescs() - START");
            List<BusinessProcDescInfo> businessProcDescInfo = businessProcDescService.findAllBusinessProcDescs();

            if (businessProcDescInfo != null && businessProcDescInfo.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(businessProcDescInfo, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error in findAllBusinessProcDescs(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/read-all-paginated", params = {"page", "sizeLimit"}, method = GET)
    @ResponseBody
    public ResponseEntity<List<BusinessProcDescInfo>> findAllBusinessProcDescPaginated(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sizeLimit", defaultValue = "100") int sizeLimit) {
        try {
            logger.info("findAllBusinessProcDescPaginated() - START" + "\n" + "page param: " + String.valueOf(page) + "\n" +
                    "sizeLimit param: " + String.valueOf(sizeLimit));
            List<BusinessProcDescInfo> businessProcDescInfo = businessProcDescService.findAllBusinessProcDescsPaginated(page, sizeLimit);

            if (businessProcDescInfo != null && businessProcDescInfo.isEmpty()) {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {

                return new ResponseEntity<>(businessProcDescInfo, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error in findAllBusinessProcDescPaginated(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //curl -X PUT --data "{\"bizProcName\":\"BizProc_1\",\"bizProcDescStr\":\"This is a business process 1\"}" http://localhost:8192/biz_proc_desc/update-biz_proc_desc
    @PutMapping("/update-biz_proc_desc")
    public ResponseEntity<String> updateBusinessProcDesc(@RequestBody BusinessProcDescInfo businessProcDescInfo) {
        try {
            logger.info("updateBusinessProcDesc() - START" + "\n" + "id param: " + String.valueOf(businessProcDescInfo.getBizProcId()));
            businessProcDescService.updateBusinessProcDesc(businessProcDescInfo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in updateBusinessProcDesc(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //curl -X DELETE http://localhost:8192/ling_var_dict/delete-{bizProcId}
    @DeleteMapping("/delete-{bizProcId}")
    public ResponseEntity<String> deleteBusinessProcDescById(@PathVariable Integer bizProcId) {
        try {
            logger.info("deleteBusinessProcDescById() - START" + "\n" + "id param: " + String.valueOf(bizProcId));
            businessProcDescService.deleteBusinessProcDescById(bizProcId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in deleteBusinessProcDescById(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}

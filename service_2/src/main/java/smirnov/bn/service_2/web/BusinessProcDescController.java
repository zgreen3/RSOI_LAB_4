package smirnov.bn.service_2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import smirnov.bn.service_2.model.BusinessProcDescInfo;
import smirnov.bn.service_2.service.BusinessProcDescService;

import java.util.List;

@RestController
@RequestMapping("/biz_proc_desc")
public class BusinessProcDescController {

    @Autowired
    private BusinessProcDescService businessProcDescService;


//curl -X POST --data "{\"bizProcName\":\"BizProc_1\",\"bizProcDescStr\":\"This is a business process 1\"}" http://localhost:8192/biz_proc_desc/create-biz_proc_desc --header "Content-Type:application/json"
//curl -X POST --data "{\"bizProcName\":\"BizProc_2\",\"bizProcDescStr\":\"This is a business process 2\"}" http://localhost:8192/biz_proc_desc/create-biz_proc_desc --header "Content-Type:application/json"
//curl -X POST --data "{\"bizProcName\":\"BizProc_3\",\"bizProcDescStr\":\"This is a business process 3\"}" http://localhost:8192/biz_proc_desc/create-biz_proc_desc --header "Content-Type:application/json"
//curl -X POST --data "{\"bizProcName\":\"BizProc_4\",\"bizProcDescStr\":\"This is a business process 4\"}" http://localhost:8192/biz_proc_desc/create-biz_proc_desc --header "Content-Type:application/json"
    @PostMapping("/create-biz_proc_desc")
    public BusinessProcDescInfo createBusinessProcDesc(@RequestBody BusinessProcDescInfo businessProcDescInfo) {
        return businessProcDescService.createBusinessProcDesc(businessProcDescInfo.getBizProcName(), businessProcDescInfo.getBizProcDescStr());
    }

    @GetMapping("/{id}")
    public BusinessProcDescInfo findBusinessProcDescById(@PathVariable Integer id) {
        return businessProcDescService.findBusinessProcDescById(id);
    }

    @GetMapping("/all")
    public List<BusinessProcDescInfo> findAllBusinessProcDesc() {
        return businessProcDescService.findAllBusinessProcDesc();
    }

    @RequestMapping(value = "/all-paginated", params = {"page", "sizeLimit"}, method = GET)
    @ResponseBody
    public List<BusinessProcDescInfo> findAllBusinessProcDescPaginated(@RequestParam(value = "page", defaultValue = "0", required = true) int page,
                                                                       @RequestParam(value = "sizeLimit", defaultValue = "100", required = true)
                                                                         int sizeLimit) {
        return businessProcDescService.findAllBusinessProcDescPaginated(page, sizeLimit);
    }
}

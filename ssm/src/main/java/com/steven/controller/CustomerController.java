package com.steven.controller;

import com.steven.common.utils.Page;
import com.steven.domain.BaseDict;
import com.steven.domain.Customer;
import com.steven.domain.wrap.CustomerWrap;
import com.steven.mapper.CustomerMapper;
import com.steven.service.BaseDictService;
import com.steven.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private BaseDictService baseDictService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping("list")
    public String queryCustomerList(CustomerWrap customerWrap, Model model) {

        // 客户来源
        List<BaseDict> fromType = this.baseDictService.queryBaseDictByDictTypeCode("002");
        // 所属行业
        List<BaseDict> industryType = this.baseDictService.queryBaseDictByDictTypeCode("001");
        // 客户级别
        List<BaseDict> levelType = this.baseDictService.queryBaseDictByDictTypeCode("006");


        // 分页查询数据
        Page<Customer> page = this.customerService.queryCustomerPageByCustWrap(customerWrap);
        // 把分页查询的结果放到模型中
        model.addAttribute("page", page);

        // 把前端页面需要显示的数据放到模型中
        model.addAttribute("fromType", fromType);
        model.addAttribute("industryType", industryType);
        model.addAttribute("levelType", levelType);

        return "customer";
    }

    @RequestMapping("edit")
    @ResponseBody //将对象经过HttpMessageConverter转换成合适的数据,因为我们先前配置了GsonHttpMessageConverter 所以这里是对javabean转换成json的转换
    public Customer edit(Long id){
        Customer customer = customerService.queryCustomerById(id);
        return customer;
    }

    @RequestMapping("update")
    @ResponseBody //这里需要加@ResponseBody注解，使其不走视图解析器
    public void update(Customer customer){
        customerService.updateCustomer(customer);
    }

    @RequestMapping("delete")
    @ResponseBody //这里需要加@ResponseBody注解，使其不走视图解析器
    public void delete(Long id){
        customerService.deleteCustomer(id);
    }


}

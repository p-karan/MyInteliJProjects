package com.training.controllers;

import com.training.DAO.MedicineDAO;
import com.training.beans.Medicine;
import com.training.ifaces.MyRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class MedicineController {

    @Autowired
    private ModelAndView mdlView;

    @Autowired
    private Medicine medicine;

    @Autowired
    private MyRespository<Medicine> dao;

    @ModelAttribute("medicineTypes")
    public String[] loadMedicineType(){
        return new String[]{"Injection", "Tablet", "Bottle", "Capsule"};
    }

    @RequestMapping(method = RequestMethod.GET, value = "/addMedicine")
    public ModelAndView init(){

        mdlView.addObject("command",medicine);
        mdlView.addObject("majorHeading","Apollo Hospitals");
        mdlView.setViewName("addMedicine");
        return mdlView;
    }

    /*@RequestMapping(method = RequestMethod.POST, value = "/addMedicine")
    public String onSubmit(@ModelAttribute("addedObject") Medicine medicine){

        int rowAdded = dao.add(medicine);
        return "success";
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "/addMedicine")
    public ModelAndView addMedicine(@ModelAttribute("addedObject") Medicine medicine){
        try {
            int rowAdded = dao.add(medicine);
            if(rowAdded == 1){
                mdlView.addObject("majorHeading","Apollo Hospitals");
                mdlView.setViewName("success");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            mdlView.addObject("majorHeading", "Apollo Hospitals");
            mdlView.addObject("errorMessage","Add Medicine Failed. Try Again !");
            mdlView.setViewName("addMedicine");
        }
        return mdlView;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/findAll")
    public ModelAndView findAll(){

        List<Map<String,Object>> listMedicines = dao.findAll();
        mdlView.addObject("showAllMedicine",listMedicines);
        mdlView.addObject("callFrom","multipleValue");
        mdlView.addObject("majorHeading","Apollo Hospitals");
        mdlView.setViewName("showAllMedicines");

        return mdlView;
    }

    @RequestMapping(value = "/findById" , method = RequestMethod.GET)
    public ModelAndView findById(){

//        return "showMedicineById";

        mdlView.addObject("showMedicineById",medicine);
        mdlView.addObject("majorHeading","Apollo Hospitals");
        mdlView.setViewName("showMedicineById");

        return mdlView;
    }

    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    public String find(@RequestParam("code") long key, Model model){

        String nextPage = "showMedicineById";
        try{
            Medicine found = dao.findById(key);
            model.addAttribute("foundoneRow", found);
            model.addAttribute("callFrom","singleValue");
            model.addAttribute("majorHeading","Apollo Hospital");
            nextPage ="showAllMedicines";
        }catch(EmptyResultDataAccessException e){
            model.addAttribute("majorHeading","Apollo Hospital");
            model.addAttribute("errorMessage","No Record Found");
        }
        return nextPage;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/Menu")
    public String displayMenu(Model model){
        model.addAttribute("majorHeading","Apollo Hospital");
        return "menu";
    }

}

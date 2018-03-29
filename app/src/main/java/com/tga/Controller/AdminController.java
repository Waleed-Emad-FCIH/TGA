package com.tga.Controller;

import com.tga.fragment.Privacy;
import com.tga.models.PrivacyModel;

/**
 * Created by root on 3/9/18.
 */

public class AdminController extends UserController {

    public AdminController(String id, String email, String pass, String name, String phoneNo, String adrs) {
        super(id, email, pass, name, phoneNo, adrs);
    }

    public void addAgent(){ }

    public void delAgent(){ }

    public void addSupervisor(){ }

    public void delSupervisor(){ }

    public void editPrivacy(String cont){
        //get privacy object from db
        PrivacyModel privacyModel = new PrivacyModel();
        privacyModel.content = cont;
    }

    /*ياسر مر من هنا */
}

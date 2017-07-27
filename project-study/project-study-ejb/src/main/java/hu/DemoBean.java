package hu;

import javax.ejb.Stateless;

/**
 * Created by martin4955 on 2017. 07. 22..
 */
@Stateless(name = "DemoEJB")
public class DemoBean {
    public DemoBean() {
    }

    public String asd() {
        return "asd";
    }
}

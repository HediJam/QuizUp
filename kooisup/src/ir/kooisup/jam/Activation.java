package ir.kooisup.jam;

import java.util.Map;

import javax.annotation.*;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@RequestScoped
public class Activation {

    private String key;
    private boolean valid = false;

  
    public void onload() {
    
      System.out.println(key);
      //check with db
      valid = true; // And auto-login if valid?
    }


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public boolean isValid() {
		return valid;
	}


	public void setValid(boolean valid) {
		this.valid = valid;
	}

    // ...
    
    
}
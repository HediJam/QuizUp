package ir.kooisup.jam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
 
@ManagedBean
@ViewScoped
public class ListCountry{
	
	private Map<String,String> countries;
	
	public ListCountry(){
		countries = new HashMap<String, String>();
		run();
	}
	
	public void run(){
		String[] locales = Locale.getISOCountries();
		
		for (String countryCode : locales ) {
			Locale obj = new Locale("", countryCode);
			countries.put(obj.getCountry(), obj.getDisplayCountry());
		}
	}
	
//	public List<String> getCountries() {
//        return (List<String>) countries.values();
//    }
	public List<String> getCountries() {

		List<String> list = new ArrayList<String>(countries.values());
		java.util.Collections.sort(list);
        return list;
    }
	
}
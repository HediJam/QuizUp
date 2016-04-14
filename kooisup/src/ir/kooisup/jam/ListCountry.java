package ir.kooisup.jam;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
 
@ManagedBean
@ViewScoped
public class ListCountry{
	
	private Map<String,String> countries;
	
	public ListCountry(){
		System.out.println("********");
		countries = new HashMap<String, String>();
		run();
	}
	
	public void run(){
		System.out.println("--------------------------------------");
		String[] locales = Locale.getISOCountries();
		
		for (String countryCode : locales ) {
			Locale obj = new Locale("", countryCode);
			System.out.println(obj.getDisplayCountry());
			countries.put(obj.getCountry(), obj.getDisplayCountry());
		}
	}
	
	public Collection<String> getCountries() {
		run();
		System.out.println("--> "+countries.size());
        return countries.values();
    }
	
}
package ir.kooisup.jam;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
 
@ManagedBean
@ViewScoped
public class ListCountry{
	private Map<String,String> countries;
	
	public ListCountry(){
		run();
	}
	
	public void run(){
		System.out.println("--------------------------------------");
		String[] locales = Locale.getISOCountries();
		
		for (String countryCode : locales ) {
			Locale obj = new Locale("", countryCode);
			
			countries.put(obj.getCountry(), obj.getDisplayCountry());
		}
	}
	
	public Collection<String> getCountries() {
		System.out.println("--> "+countries.size());
        return countries.values();
    }
	
}
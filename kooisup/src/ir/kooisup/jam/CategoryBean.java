package ir.kooisup.jam;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class CategoryBean {
	private List<String> categoryList;
	private DBHandler db = DBHandler.getInstance();
	
	public CategoryBean() {
		// TODO Auto-generated constructor stub
		db.insertCategory("MATH");
		db.insertCategory("ART");
		db.insertCategory("SCIENCE");
		db.insertCategory("MATH2");
		db.insertCategory("ART2");
		db.insertCategory("SCIENCE2");
		
		this.categoryList = db.findCategories();
		
	}
	
	public List<String> getCategoryList(){
		return categoryList;
	}
}

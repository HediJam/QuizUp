package ir.kooisup.jam;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class CategoryBean {
	private List<Category> categoryList;
	private DBHandler db = DBHandler.getInstance();
	private String currentCategory = null;
	
	private List<Category> filteredMessages;  
	
	public CategoryBean() {
		// TODO Auto-generated constructor stub
		this.categoryList = new ArrayList<CategoryBean.Category>();
		
		if(db.findCategory("ریاضی") == null)
			db.insertCategory("ریاضی");
		if(db.findCategory("هنر") == null)
			db.insertCategory("هنر");
		if(db.findCategory("علوم") == null)
			db.insertCategory("علوم");
		if(db.findCategory("عکاسی") == null)
			db.insertCategory("عکاسی");
		if(db.findCategory("موسیقی") == null)
			db.insertCategory("موسیقی");
		if(db.findCategory("ورزش") == null)
			db.insertCategory("ورزش");
		
		List<String> cats = db.findCategories();
		for (int i = 0; i < cats.size(); i++) {
			Category cat = new Category();
			cat.setName(cats.get(i));
			cat.setFileName((i+1)+"jpg");
			this.categoryList.add(cat);
		}
		  
		
	}
	
	public List<Category> getCategoryList(){
		return categoryList;
	}
	
	public String getCurrentCategory() {
		return currentCategory;
	}
	
	public void setCurrentCategory(String cat) {
		currentCategory = cat;
	}
	
	public List<Category> getFilteredMessages() {  
        return filteredMessages;  
    }  
  
    public void setFilteredMessages(List<Category> filteredMessages) {  
        this.filteredMessages = filteredMessages;  
    }  
    
    public String deleteAction(Category cat) {
    	categoryList.remove(cat);
    	return null;
    }
    
    public String addCategory(){
    	Category cat = new Category();
    	cat.setName(currentCategory);
    	categoryList.add(cat);
    	return null;
     }

    public class Category{
    	private String name;
    	private String fileName;
    	
    	public final String getName() {  
            return name;  
        }  
  
        public final void setName(String name) {  
            this.name = name;  
        }

		public final String getFileName() {
			return fileName;
		}

		public final void setFileName(String fileName) {
			this.fileName = fileName;
		}
    }

}



package ir.kooisup.jam;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;

@ManagedBean
@SessionScoped
public class CategoryBean {
	private List<Category> categoryList;
	private List<Category> backupList;
	private DBHandler db = DBHandler.getInstance();
	private String currentCategory = null;
	private List<Category> filteredMessages;

	public CategoryBean() {
		// TODO Auto-generated constructor stub
		this.categoryList = new ArrayList<CategoryBean.Category>();
		this.backupList = new ArrayList<CategoryBean.Category>();
		/*
		 * if(db.findCategory("ریاضی") == null) db.insertCategory("ریاضی");
		 * if(db.findCategory("هنر") == null) db.insertCategory("هنر");
		 * if(db.findCategory("علوم") == null) db.insertCategory("علوم");
		 * if(db.findCategory("عکاسی") == null) db.insertCategory("عکاسی");
		 * if(db.findCategory("موسیقی") == null) db.insertCategory("موسیقی");
		 * if(db.findCategory("ورزش") == null) db.insertCategory("ورزش");
		 */

		List<String> cats = db.findCategories();
		for (int i = 0; i < cats.size(); i++) {
			Category cat = new Category();
			cat.setName(cats.get(i));
			cat.setFileName((i + 1) + ".jpg");
			this.categoryList.add(cat);
		}

		for (Category c : categoryList) {
			Category cc = new Category();
			cc.setName(c.getName());
			cc.setFileName(c.getFileName());
			backupList.add(cc);
		}

	}

	public List<Category> getCategoryList() {
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
		
		System.out.println("removing catgory ..... " +  cat.getName());
		categoryList.remove(cat);
		backupList.remove(cat);
		db.removeCategory(cat.getName());
		return null;
	}

	public String addCategory() {
		Category cat = new Category();
		cat.setName(currentCategory);
		categoryList.add(cat);
		backupList.add(cat);
		db.insertCategory(currentCategory);
		return null;
	}

	public void editCategory(RowEditEvent event) {
		String oldCategory = "";
		String newCategory = "";
		for (int i = 0; i < categoryList.size(); i++) {
			
			if(! (categoryList.get(i).getName().equals(backupList.get(i).getName()))){
				oldCategory = backupList.get(i).getName();
				newCategory = categoryList.get(i).getName();
			}
		}
		
		System.out.println("edittttttttt " + oldCategory + "   " + newCategory);
		db.updateCategory(oldCategory, newCategory);
	}

	public class Category {
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

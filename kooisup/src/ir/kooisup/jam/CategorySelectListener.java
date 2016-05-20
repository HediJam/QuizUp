package ir.kooisup.jam;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class CategorySelectListener implements ActionListener{
	   @Override
	   public void processAction(ActionEvent arg0) throws AbortProcessingException {
		 //access userData bean directly
		      CatsNQuestions catsNQuestions = (CatsNQuestions) FacesContext.getCurrentInstance().
		         getExternalContext().getSessionMap().get("catsNQuestions");
		      catsNQuestions.setSelectedCategory("");
	   }
}
package br.com.mastermenu.converter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mastermenu.model.Ingrediente;

@FacesConverter("contratoConverter")
public class EntityConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if(value != null && !value.isEmpty()){
            return (Ingrediente) uic.getAttributes().get(value);
        }
        return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
		//verificando se o objeto é uma instancia da classe
    	System.out.println(obj);
        if(obj instanceof Ingrediente) {
                        //obtendo o objeto
        	Ingrediente entity = (Ingrediente) obj;                        
            uic.getAttributes().put(String.valueOf(entity.getIdIngrediente()) , entity);
                        //retornando o codigo do serviço
            return String.valueOf(entity.getIdIngrediente());
        }
        return "";
	}
} 
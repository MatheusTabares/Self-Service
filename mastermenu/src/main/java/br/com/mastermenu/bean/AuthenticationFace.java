package br.com.mastermenu.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.SocialAuthUtil;

@ManagedBean
@SessionScoped
public class AuthenticationFace implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private SocialAuthManager socialManager;
	private Profile perfil;
	final String mainUrl= "http://localhost:8080/mastermenu/loginCliente.xhtml";
	final String sucessoUrl= "http://localhost:8080/mastermenu/homeCliente.xhtml";
	final String provedorId="facebook";
	
	
		
	public Profile getPerfil() {
		return perfil;
	}
	public void setPerfil(Profile perfil) {
		this.perfil = perfil;
	}
	
	public void conectar() throws Exception{
		Properties props = System.getProperties();
		props.put("graph.facebook.com.consumer_key", "352420591764024");
		props.put("graph.facebook.com.consumer_secret","3d20472ae5a913dd70a7569cf1d4b9cf");
		props.put("graph.facebook.com.custom_permissions", "public_profile, email");
		
		SocialAuthConfig config = SocialAuthConfig.getDefault();
		config.load(props);
		socialManager = new SocialAuthManager();
		socialManager.setSocialAuthConfig(config);
		
		String autenticacaoUrl= socialManager.getAuthenticationUrl(provedorId, sucessoUrl);
		FacesContext.getCurrentInstance().getExternalContext().redirect(autenticacaoUrl);
	
	}
	
	public void getProfile() {
		try {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest resquest = (HttpServletRequest)externalContext.getRequest();
		Map<String, String> map = SocialAuthUtil.getRequestParametersMap(resquest);
		
		if(socialManager != null){
			
				AuthProvider provedor = socialManager.connect(map);
				this.perfil = provedor.getUserProfile();
				
			}
		
		FacesContext.getCurrentInstance().getExternalContext().redirect(sucessoUrl);
		
		}catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
	
	

}

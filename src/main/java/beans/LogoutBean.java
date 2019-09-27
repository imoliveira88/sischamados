package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import servico.DAOGenericoJPA;

@ManagedBean(name = "logoutBean")
@ViewScoped
public class LogoutBean extends Artificial implements Serializable {

    public String logout() throws ServletException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
            DAOGenericoJPA.fecharFabrica();
        }
        
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();        
        request.logout();
        this.adicionaMensagem("Sessão encerrada!", "destinoAviso", "SUCESSO!");
        return "/login.xhtml?faces-redirect=true";
    }
}


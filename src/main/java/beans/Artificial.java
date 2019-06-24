/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Artificial {
    public void adicionaMensagem(String mensagem, String destino, String parecer){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        msg = new FacesMessage(parecer,mensagem);
        context.addMessage(destino, msg);
        
    }
}

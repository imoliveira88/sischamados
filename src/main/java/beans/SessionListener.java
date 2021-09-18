/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
 
public class SessionListener implements HttpSessionListener {
 
    public static int activeSessionCount;
    public static List<HttpSession> sessoes;
 
    public static int getActiveSessionCount() {
        return activeSessionCount;
    }
 
    @Override
    public void sessionCreated(HttpSessionEvent createObj) {
        activeSessionCount++;
        HttpSession sessao = createObj.getSession();
        if(activeSessionCount == 1) sessoes = new ArrayList<>();
        sessoes.add(sessao);
    }
 
    @Override
    public void sessionDestroyed(HttpSessionEvent destroyObj) {
        activeSessionCount--;
        HttpSession sessao = destroyObj.getSession();
        sessoes.remove(sessao);
    }
}
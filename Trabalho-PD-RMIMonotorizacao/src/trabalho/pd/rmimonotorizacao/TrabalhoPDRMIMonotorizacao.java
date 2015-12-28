/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.pd.rmimonotorizacao;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import trabalho.de.pd.servidor.RMIServidorInterface;
import trabalho.de.pd.servidor.TrabalhoPDRMIMonotorizacaoInterface;

/**
 *
 * @author ASUS
 */
public class TrabalhoPDRMIMonotorizacao extends UnicastRemoteObject implements TrabalhoPDRMIMonotorizacaoInterface{

    public TrabalhoPDRMIMonotorizacao() throws RemoteException {}
    
    @Override
    public void notifyNewOperationConcluded(String description) throws RemoteException {
        System.out.println(description);
    }
    
        
    public static void main(String[] args) {
        // TODO code application logic here
        try{
 			
            //Cria e lanca o servico 
            TrabalhoPDRMIMonotorizacao observer = new TrabalhoPDRMIMonotorizacao();
            System.out.println("Servico TrabalhoPDRMIMonotorizacao criado e em execucao...");
            
            //Localiza o servico remoto nomeado "GetRemoteFile"
            String objectUrl = "rmi://127.0.0.1/GetRemoteFile"; //rmiregistry on localhost
            
            if(args.length > 0)
                objectUrl = "rmi://"+args[0]+"/RMITrabalho"; 
                            
            RMIServidorInterface getRemoteService = (RMIServidorInterface)Naming.lookup(objectUrl);
            
            //adiciona observador no servico remoto
            getRemoteService.addObserver(observer);
            
            System.out.println("<Enter> para terminar...");
            System.out.println();
            System.in.read();
            
            getRemoteService.removeObserver(observer);
            UnicastRemoteObject.unexportObject(observer, true);
            
        }catch(RemoteException e){
            System.out.println("Erro remoto - " + e);
            System.exit(1);
        }catch(Exception e){
            System.out.println("Erro - " + e);
            System.exit(1);
        }  
    }
    
}

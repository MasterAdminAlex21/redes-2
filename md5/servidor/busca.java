import java.rmi.Remote;
import java.rmi.RemoteException;

public interface busca extends Remote{
 //boolean busquedaMD5(byte[] MD5) throws RemoteException;
 boolean busquedaArc(String archivo,byte[] md5) throws RemoteException;
}
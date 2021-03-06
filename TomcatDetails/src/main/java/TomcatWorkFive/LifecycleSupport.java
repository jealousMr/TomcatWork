package TomcatWorkFive;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

public final class LifecycleSupport {
    private Lifecycle lifecycle=null;
    private LifecycleListener listeners[]=new LifecycleListener[0];
    public LifecycleSupport(Lifecycle lifecycle){
        super();
        this.lifecycle=lifecycle;
    }
    public void addLifecycleListener(LifecycleListener listener){
        synchronized (listeners){
            LifecycleListener results[]=new LifecycleListener[listeners.length+1];
            for(int i=0;i<listeners.length;i++)
                results[i]=listeners[i];
            results[listeners.length]=listener;
            listeners=results;
        }
    }

    public LifecycleListener[] findLifecycleListeners() {
        return listeners;
    }
    public void fireLifecycleEvent(String type,Object data){
        LifecycleEvent event=new LifecycleEvent(lifecycle,type,data);
        LifecycleListener interested[]=null;
        synchronized (listeners){
            interested=listeners.clone();
        }for (int i=0;i<interested.length;i++)
            interested[i].lifecycleEvent(event);
    }
    public void removeLifecycleListener(LifecycleListener listener){
        synchronized (listeners){
            int n=-1;
            for (int i=0;i<listeners.length;i++){
                if(listeners[i]==listener){
                    n=i;
                    break;
                }
            }
            if(n<0)
                return;
            LifecycleListener res[]=new LifecycleListener[listeners.length-1];
            int j=0;
            for(int i=0;i<listeners.length-1;i++){
                if(i!=n)
                    res[j++]=listeners[i];
            }
            listeners=res;
        }
    }
}

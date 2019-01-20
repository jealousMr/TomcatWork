package TomcatWorkFour;


import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;


public class SimplePipeline implements Pipeline {
    protected  Valve basic=null;
    protected Container container=null;
    protected Valve valves[]=new Valve[0];

    public SimplePipeline(Container container){
        setContainer(container);
    }

    public void setContainer(Container container){
        this.container=container;
    }

    public void setValves(Valve[] valves) {
        this.valves = valves;
    }

    @Override
    public Valve getBasic() {
        return basic;
    }

    @Override
    public void setBasic(Valve valve) {
        this.basic=valve;
        ((Contained)valve).setContainer(container);
    }

    @Override
    public void addValve(Valve valve) {
        if(valve instanceof Contained)
            ((Contained)valve).setContainer(this.container);
        synchronized (valves){
            Valve res[]=new Valve[valves.length+1];
            System.arraycopy(valves,0,res,0,valves.length);
            res[valves.length]=valve;
            valves=res;
        }
    }

    @Override
    public Valve[] getValves() {
        return valves;
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        (new SimplePipelineValueContext()).invokeNext(request,response);
    }

    @Override
    public void removeValve(Valve valve) {

    }
    class SimplePipelineValueContext implements ValveContext{
        protected int stage=0;
        @Override
        public String getInfo() {
            return null;
        }

        @Override
        public void invokeNext(Request request, Response response) throws IOException, ServletException {
            int subscript = stage;
            stage = stage + 1;
            if (subscript < valves.length) {
                valves[subscript].invoke(request, response, this);
            }
            else if ((subscript == valves.length) && (basic != null)) {
                basic.invoke(request, response, this);
            }
            else {
                throw new ServletException("No valve");
            }
        }
    }
}

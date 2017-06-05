package modelling;

import modelling.entities.ModellingConnection;
import modelling.entities.ModellingProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadgehog on 21.04.2014.
 */
public class GantViewEntity {
    public final static String SIMPLE_WORK_STATE = "-";
    private final ModellingProcessor processor;
    private final ModellingConnection connection;
    public final GantEntityType gantEntityType;
    private String name = "";
    private int linkId;
    private List<String> state;

    public GantViewEntity(ModellingProcessor processor, ModellingConnection connection, GantEntityType gantEntityType, int linkId) {
        this.processor = processor;
        this.connection = connection;
        this.gantEntityType = gantEntityType;
        this.linkId = linkId;
        state = new ArrayList<>();
        name = "p" + processor.getProcessor().getId();
        if (gantEntityType == GantEntityType.CONNECTION)
            name += "_L" + linkId;
    }

    public GantViewEntity(ModellingProcessor processor, GantEntityType gantEntityType, int linkId) {
        this(processor, null, gantEntityType, linkId);
    }

    public GantViewEntity(ModellingProcessor processor, GantEntityType gantEntityType) {
        this(processor, gantEntityType, -1);
    }

    public GantViewEntity(ModellingConnection connection, GantEntityType gantEntityType, int linkId) {
        this(null, connection, gantEntityType, linkId);
    }

    public void addStateStep(String stateStr) {
        state.add(stateStr);
    }

    public void addStateStep(String stateStr, int pos) {
        try {
            while (state.size() < pos) {
                state.add(SIMPLE_WORK_STATE);
            }
            state.add(stateStr);
        } catch (OutOfMemoryError error) {
            System.out.println(">>>>>>>>" + pos);
        }
    }

    public List<String> getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getStep(int step) {
        return state.size() - 1 < step ? SIMPLE_WORK_STATE : state.get(step);
    }

    public ModellingProcessor getProcessor() {
        return processor;
    }

    public ModellingConnection getConnection() {
        return connection;
    }

    public enum GantEntityType {
        PROCESSOR, CONNECTION
    }
}

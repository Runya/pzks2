package entities.computerSystem;

import java.io.*;
import java.util.*;

/**
 * Created by hadgehog on 16.02.14.
 */
public class ComputerSystem implements Serializable {

    private int INFINITY = (Integer.MAX_VALUE / 2) - 1;

    private List<Processor> processors;
    private List<SystemConnection> systemConnections;

    private Map<Integer, Integer> processorIdPos = new HashMap<>();
    private Map<Integer, Integer> processorPosId = new HashMap<>();
    private int[][] adjacencyMatrix;


    public ComputerSystem() {
        processors = new ArrayList<>();
        systemConnections = new ArrayList<>();
    }

    public int addProcessor(int x, int y) {
        int id = defineNewId();
        Processor processor = new Processor(id, x, y);
        processors.add(processor);
        createAdjacencyMatrix();
        return id;
    }

    public double getProcessorPower(int id) {
        return getProcessor(id).getPower();
    }

    public double getProcessorPower() {
        return Processor.DEFAULT_POWER;
    }

    public double getLinkBandwidth() {
        return SystemConnection.DEFAULT_BANDWIDTH;
    }

    public int getPhysicalLinkNumber() {
        return SystemConnection.DEFAULT_PHYSICAL_LINK_NUMBER;
    }


    public boolean getDuplex() {
        return SystemConnection.DUPLEX;
    }

    public boolean getInOutProcessor() {
        return Processor.IN_OUT_PROCESSOR;
    }

    public void setInOutProcessor(boolean inOut) {
        Processor.IN_OUT_PROCESSOR = inOut;
    }

    public void setLinkBandwidth(double newBandwidth) {
        SystemConnection.DEFAULT_BANDWIDTH = newBandwidth;
        for (SystemConnection systemConnection : systemConnections) {
            systemConnection.setBandwidth(newBandwidth);
        }
    }

    public void setProcessorsPower(double newPower) {
        Processor.DEFAULT_POWER = newPower;
        for (Processor processor : processors) {
            processor.setPower(newPower);
        }
    }

    public void readFromFile(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream oin = new ObjectInputStream(fis);
        ComputerSystem computerSystemGraph = (ComputerSystem) oin.readObject();
        this.processors = computerSystemGraph.getProcessors();
        this.systemConnections = computerSystemGraph.getSystemConnections();
        createAdjacencyMatrix();
    }

    public void saveToFile(String fileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Processor> getProcessors() {
        return processors;
    }

    public List<SystemConnection> getSystemConnections() {
        return systemConnections;
    }

    private int defineNewId() {
        int id = -1;
        boolean idExist = true;
        while (idExist) {
            id++;
            idExist = false;
            for (Processor processor : processors) {
                if (processor.getId() == id) {
                    idExist = true;
                    break;
                }
            }
        }
        return id;
    }

    private Processor getProcessor(int id) {
        for (Processor processor : processors) {
            if (processor.getId() == id) {
                return processor;
            }
        }
        return null;
    }

    public int[][] getProcessorData() {
        int[][] processorsData = new int[processors.size()][3];// [id, weight, x, y]
        for (int i = 0; i < processorsData.length; i++) {
            processorsData[i][0] = processors.get(i).getId();
            processorsData[i][1] = processors.get(i).getX();
            processorsData[i][2] = processors.get(i).getY();
        }
        return processorsData;
    }

    public int[] getProcessorData(int id) {
        Processor processor = getProcessor(id);
        int[] processorData = new int[4];//// [id, weight, x, y]
        processorData[0] = processor.getId();
        processorData[1] = processor.getX();
        processorData[2] = processor.getY();
        return processorData;
    }

    public int[][] getConnectionsData() {
        List<int[]> connectionsDataList = new ArrayList<>();
        for (Processor processor : processors) {
            Set<Integer> dependsFrom = processor.getDependsFrom();
            for (Integer depend : dependsFrom) {
                int[] connection = {depend, processor.getId()};
                connectionsDataList.add(connection);
            }
        }
        int[][] connectionDataArray = new int[connectionsDataList.size()][];
        for (int i = 0; i < connectionsDataList.size(); i++) {
            connectionDataArray[i] = connectionsDataList.get(i);
        }
        return connectionDataArray;
    }

    public double getConnectionBandwidth(int fromId, int toId) {
        return getProcessorConnection(fromId, toId).getBandwidth();
    }

    public SystemConnection getProcessorConnection(int fromId, int toId) {
        for (SystemConnection systemConnection : systemConnections) {
            if (systemConnection.getFromProcessorId() == fromId & systemConnection.getToProcessorId() == toId) {
                return systemConnection;
            }
            if (systemConnection.getFromProcessorId() == toId & systemConnection.getToProcessorId() == fromId) {
                return systemConnection;
            }
        }
        return null;
    }

    public void removeProcessor(int id) {
        Processor processor = getProcessor(id);
        processors.remove(processor);
        for (Processor processor1 : processors) {
            processor1.getDependsFrom().remove(Integer.valueOf(id));
        }


        for (int i = 0; i < systemConnections.size(); i++) {
            SystemConnection systemConnection = systemConnections.get(i);
            if (systemConnection.getFromProcessorId() == id | systemConnection.getToProcessorId() == id) {
                systemConnections.remove(systemConnection);
            }
        }
        createAdjacencyMatrix();
    }

    public void setProcessorData(int id, int newX, int newY) {
        Processor processor = getProcessor(id);
        processor.setX(newX);
        processor.setY(newY);
    }

    public void removeDepend(int dependsFrom, int taskId) {
        Processor processor = getProcessor(taskId);
        processor.removeDependsFrom(dependsFrom);
        systemConnections.remove(getSystemConnection(dependsFrom, taskId));
        createAdjacencyMatrix();
    }

    public SystemConnection getSystemConnection(int fromId, int toId) {
        for (SystemConnection systemConnection : systemConnections) {
            if ((systemConnection.getFromProcessorId() == fromId & systemConnection.getToProcessorId() == toId) |
                    (systemConnection.getFromProcessorId() == toId & systemConnection.getToProcessorId() == fromId)) {
                return systemConnection;
            }
        }
        return null;
    }

    public boolean addDepend(int processorId, int dependsFrom) {
        //зв'язок "сам на себе"
        if (processorId == dependsFrom) {
            return false;
        }
        Processor processor = getProcessor(processorId);
        if (processor.getDependsFrom().contains(dependsFrom)) {
            return false;
        }
        Processor dependProcessor = getProcessor(dependsFrom);
        if (dependProcessor.getDependsFrom().contains(processorId)) {
            return false;
        }
        processor.addDependsFrom(dependsFrom);
        SystemConnection systemConnection = new SystemConnection(dependsFrom, processorId);
        systemConnections.add(systemConnection);
        createAdjacencyMatrix();
        return true;//true - додано, false - буде цикл
    }

    public void removeAllProcessors() {
        processors = new ArrayList<>();
        systemConnections = new ArrayList<>();
        createAdjacencyMatrix();
    }

    public void setNewBandwidth(int fromId, int toId, double newBandwidth) {
        getSystemConnection(fromId, toId).setBandwidth(newBandwidth);
    }

    public void setNewPower(int id, double newPower) {
        getProcessor(id).setPower(newPower);
    }

    public boolean getSystemConnectivity() {
        if (processors.size() == 0) {
            return true;
        }
        int[][] minDistances = getMinDistancesMatrix();
        for (int[] minDistance : minDistances) {
            for (int i : minDistance) {
                if (i == INFINITY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void createAdjacencyMatrix() {
        processorIdPos = new HashMap<>();
        for (int i = 0; i < processors.size(); i++) {
            processorIdPos.put(processors.get(i).getId(), i);
            processorPosId.put(i, processors.get(i).getId());
        }
        adjacencyMatrix = new int[processors.size()][processors.size()];
        for (int i = 0; i < processors.size(); i++) {
            Set<Integer> dependsFrom = processors.get(i).getDependsFrom();
            for (Integer depend : dependsFrom) {
                adjacencyMatrix[i][processorIdPos.get(depend)] = 1;
                adjacencyMatrix[processorIdPos.get(depend)][i] = 1;
            }
        }
//        int count = 0;
//        for (int[] ints : adjacencyMatrix) {
////            System.out.print(processorIdPos.get(count++)+":");
//            for (int anInt : ints) {
//                System.out.print(anInt + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
        getMinDistancesMatrix();
    }

    /**
     * Floyd–Warshall algorithm
     */
    private int[][] getMinDistancesMatrix() {
        int p = processors.size();
        int[][] minDistance = new int[p][p];
        for (int i = 0; i < p; i++) {
            for (int k = 0; k < p; k++) {
                minDistance[i][k] = (adjacencyMatrix[i][k] == 0) ? INFINITY : adjacencyMatrix[i][k];
            }
        }
        for (int k = 0; k < p; k++) {
            for (int i = 0; i < p; i++) {
                for (int j = 0; j < p; j++) {
                    minDistance[i][j] = Math.min(minDistance[i][j], minDistance[i][k] + minDistance[k][j]);
                }
            }
        }
        for (int i = 0; i < minDistance.length; i++) {
            minDistance[i][i] = 0;
        }
//        for (int i = 0; i < minDistance.length; i++) {
//            for (int j = 0; j < minDistance[0].length; j++) {
//                System.out.print(minDistance[i][j] + " ");
//            }
//            System.out.println();
//        }
        return minDistance;
    }

    public void setDuplex(boolean duplex) {
        SystemConnection.DUPLEX = duplex;
    }

    public void setDefaultPhysicalLinkNumber(int number) {
        SystemConnection.DEFAULT_PHYSICAL_LINK_NUMBER = number;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public int getProcessorConnectivity(Processor processor) {
        int id = processor.getId();
        int pos = processorIdPos.get(id);
        int count = 0;
        for (int i = 0; i < adjacencyMatrix[pos].length; i++) {
            if (adjacencyMatrix[pos][i] == 1) count++;
        }
//        System.out.println("id= " + processor.getId() + " c=" + count);
        return count;
    }

    public int getMinWayValue(int fromId, int toId) {
        if (fromId == toId) return 0;
        //Дейкстра
        int curPos = processorIdPos.get(fromId);
        int toPos = processorIdPos.get(toId);
        int[] stopVertex = new int[processors.size()];
        stopVertex[curPos] = 1;
        int[] prevState = new int[processors.size()];
        int[] curState = new int[processors.size()];
        for (int i = 0; i < prevState.length; i++) {
            prevState[i] = INFINITY;
        }
        int minValue = INFINITY;
        prevState[curPos] = 0;
        while (stopVertex[toPos] != 1) {
            for (int i = 0; i < curState.length; i++) {
                int wayToI = INFINITY;
                if (adjacencyMatrix[curPos][i] == 1) {
                    wayToI = (int) getConnectionBandwidth(processorPosId.get(curPos), processorPosId.get(i));
                }
                curState[i] = stopVertex[i] == 1 ? prevState[i] : Math.min(prevState[i], prevState[curPos] + wayToI);
            }
            //search min value
            int minValuePos = curPos;
            minValue = INFINITY;
            for (int i = 0; i < curState.length; i++) {
                if (stopVertex[i] != 1) {
                    if (curState[i] < minValue) {
                        minValue = curState[i];
                        minValuePos = i;
                    }
                }
            }
            stopVertex[minValuePos] = 1;
            prevState = curState;
            curState = new int[prevState.length];
            curPos = minValuePos;
        }
        return minValue;
    }

    public int getMinWayValue(Processor processor1, Processor processor2) {
        return getMinWayValue(processor1.getId(), processor2.getId());
    }

    public int[] getMinWay(int fromId, int toId) {
        Map<Integer, Integer> curPrevVertex = new HashMap<>();
        //Дейкстра
        int startPos = processorIdPos.get(fromId);
        curPrevVertex.put(startPos, startPos);
        int curPos = startPos;
        int toPos = processorIdPos.get(toId);
        int[] stopVertex = new int[processors.size()];
        stopVertex[curPos] = 1;
        int[] prevState = new int[processors.size()];
        int[] curState = new int[processors.size()];
        for (int i = 0; i < prevState.length; i++) {
            prevState[i] = INFINITY;
        }
        int minValue = INFINITY;
        prevState[curPos] = 0;
        while (stopVertex[toPos] != 1) {
            for (int i = 0; i < curState.length; i++) {
                int wayToI = INFINITY;
                if (adjacencyMatrix[curPos][i] == 1) {
                    wayToI = (int) getConnectionBandwidth(processorPosId.get(curPos), processorPosId.get(i));
                }
                if (stopVertex[i] == 1) {
                    curState[i] = prevState[i];
                } else {
                    curState[i] = Math.min(prevState[i], prevState[curPos] + wayToI);
                    if (curState[i] != prevState[i]) curPrevVertex.put(i, curPos);
                }
            }
            //search min value
            int minValuePos = curPos;
            minValue = INFINITY;
            for (int i = 0; i < curState.length; i++) {
                if (stopVertex[i] != 1) {
                    if (curState[i] < minValue) {
                        minValue = curState[i];
                        minValuePos = i;
                    }
                }
            }
            stopVertex[minValuePos] = 1;
            prevState = curState;
            curState = new int[prevState.length];
//            curPrevVertex.put(minValuePos, curPos);
            curPos = minValuePos;
        }
        List<Integer> minWayList = new ArrayList<>();
        minWayList.add(toPos);
        curPos = curPrevVertex.get(toPos);
        while (curPos != startPos) {
            minWayList.add(curPos);
            curPos = curPrevVertex.get(curPos);
        }
        minWayList.add(startPos);
        int[] minWay = new int[minWayList.size()];
        for (int i = 0; i < minWay.length; i++) {
            minWay[i] = minWayList.get(minWay.length - i - 1);
        }
        return minWay;
    }
}

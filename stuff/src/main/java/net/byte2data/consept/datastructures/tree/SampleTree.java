package net.byte2data.consept.datastructures.tree;

import java.util.ArrayList;
import java.util.List;

public class SampleTree {

    private TreeNode rootTree;

    public SampleTree(int rootData){
        rootTree = new TreeNode(rootData,Position.ROOT);
    }

    private enum Position{
        ROOT,LEFT,RIGHT,LEAF;
    }

    public class TreeNode{
        private int nodeData;
        private Position nodePosition;
        private List<TreeNode> childNodes;

        public TreeNode(int data, Position position){
            this.nodeData =data;
            this.nodePosition=position;
            childNodes = new ArrayList<>();
        }

        public void addChild(TreeNode childNode){
            childNodes.add(childNode);
        }

        public TreeNode getChild(Position position){
            if(null!=this.childNodes){
                for(TreeNode treeNode : this.childNodes){
                    if(treeNode.nodePosition.equals(position))
                        return treeNode;
                }
            }
            return null;
        }
    }

    public static void main(String... args){
        SampleTree sampleTree = new SampleTree(1);

        SampleTree.TreeNode treeRootNode = sampleTree. new TreeNode(1,Position.ROOT);
        SampleTree.TreeNode treeRootLeftNode = sampleTree. new TreeNode(2,Position.LEFT);
        SampleTree.TreeNode treeRootRightNode = sampleTree. new TreeNode(3,Position.RIGHT);
        SampleTree.TreeNode treeRootLeft1Node = sampleTree. new TreeNode(4,Position.LEFT);
        SampleTree.TreeNode treeRootLeft2Node = sampleTree. new TreeNode(6,Position.LEFT);
        SampleTree.TreeNode treeRootRigh1tNode = sampleTree. new TreeNode(5,Position.RIGHT);
        SampleTree.TreeNode treeRootRigh2tNode = sampleTree. new TreeNode(7,Position.RIGHT);
        treeRootNode.addChild(treeRootLeftNode);
        treeRootNode.addChild(treeRootRightNode);
        treeRootLeftNode.addChild(treeRootLeft1Node);
        treeRootLeftNode.addChild(treeRootLeft2Node);
        treeRootRightNode.addChild(treeRootRigh1tNode);
        treeRootRightNode.addChild(treeRootRigh2tNode);


/*


{

"class": "go.GraphLinksModel",


"linkFromPortIdProperty": "fromPort",


"linkToPortIdProperty": "toPort",


"nodeDataArray":
[
{"category":"Start", "type":"Start", "key":-1, "loc":"-225 -189"},
{"category":"Question", "type":"Question", "questionBody":"", "key":-2, "loc":"-99 -124"},
{"category":"Answer", "type":"Answer", "answerKey":"", "answerValue":"", "key":-3, "loc":"61 -131"},
{"category":"End", "type":"End", "key":-4, "loc":"146 -154"}
],


"linkDataArray":
[
{"from":-1, "to":-2, "fromPort":"R", "toPort":"L", "points":[-200.73282552319904,-189,-190.73282552319904,-189,-165.74461329260538,-189,-165.74461329260538,-124,-140.75640106201172,-124,-130.75640106201172,-124]},
{"from":-2, "to":-3, "fromPort":"R", "toPort":"L", "points":[-67.24359893798828,-124,-57.24359893798828,-124,-16.35750961303711,-124,-16.35750961303711,-131,24.528579711914062,-131,34.52857971191406,-131]},
{"from":-3, "to":-4, "fromPort":"R", "toPort":"L", "points":[87.47142028808594,-131,97.47142028808594,-131,106.6772627719613,-131,106.6772627719613,-154,115.88310525583667,-154,125.88310525583667,-154]}
]

}


*/


        String json = "{\n" +
                "\n" +
                "\"class\": \"go.GraphLinksModel\",\n" +
                "\n" +
                "\n" +
                "\"linkFromPortIdProperty\": \"fromPort\",\n" +
                "\n" +
                "\n" +
                "\"linkToPortIdProperty\": \"toPort\",\n" +
                "\n" +
                "\n" +
                "\"nodeDataArray\":\n" +
                "[\n" +
                "{\"category\":\"Start\", \"type\":\"Start\", \"key\":-1, \"loc\":\"-225 -189\"},\n" +
                "{\"category\":\"Question\", \"type\":\"Question\", \"questionBody\":\"\", \"key\":-2, \"loc\":\"-99 -124\"},\n" +
                "{\"category\":\"Answer\", \"type\":\"Answer\", \"answerKey\":\"\", \"answerValue\":\"\", \"key\":-3, \"loc\":\"61 -131\"},\n" +
                "{\"category\":\"End\", \"type\":\"End\", \"key\":-4, \"loc\":\"146 -154\"}\n" +
                "],\n" +
                "\n" +
                "\n" +
                "\"linkDataArray\":\n" +
                "[\n" +
                "{\"from\":-1, \"to\":-2, \"fromPort\":\"R\", \"toPort\":\"L\", \"points\":[-200.73282552319904,-189,-190.73282552319904,-189,-165.74461329260538,-189,-165.74461329260538,-124,-140.75640106201172,-124,-130.75640106201172,-124]},\n" +
                "{\"from\":-2, \"to\":-3, \"fromPort\":\"R\", \"toPort\":\"L\", \"points\":[-67.24359893798828,-124,-57.24359893798828,-124,-16.35750961303711,-124,-16.35750961303711,-131,24.528579711914062,-131,34.52857971191406,-131]},\n" +
                "{\"from\":-3, \"to\":-4, \"fromPort\":\"R\", \"toPort\":\"L\", \"points\":[87.47142028808594,-131,97.47142028808594,-131,106.6772627719613,-131,106.6772627719613,-154,115.88310525583667,-154,125.88310525583667,-154]}\n" +
                "]\n" +
                "\n" +
                "}";
        String nodeDataArrayPart = json.substring(json.indexOf("nodeDataArray")+15, json.indexOf("linkDataArray"));
        nodeDataArrayPart = nodeDataArrayPart.substring(nodeDataArrayPart.indexOf("[")+1,nodeDataArrayPart.indexOf("]"));
        //System.out.println("nodeDataArrayPart:"+nodeDataArrayPart);

        String[] nodeDataArray = nodeDataArrayPart.split("}");

        String nodeDataStart = "";
        String nodeDataStartKey = "";

        String nodeDataEnd = "";
        String nodeDataEndKey = "";

        String[] nodeDataQuestion = new String[10];
        String nodeDataQuestionKey = "";
        String nodeDataQuestionQuestionBody = "";

        String[] nodeDataAnswer = new String[50];
        String nodeDataAnswerKey = "";
        String nodeDataAnswerAnswerKey = "";
        String nodeDataAnswerAnswerValue = "";

        int nodeDataQuestionIndex = 0;
        int nodeDataAnswerIndex = 0;
        for(String part : nodeDataArray){
            if((null!=part) && (part.length()>5)){
                part = part.trim();
                part=part.substring(part.indexOf("{\"")+1);
                System.out.println("part: " +part);
                if (part.lastIndexOf("\"category\":\"Start\"")>=0){
                    nodeDataStart=part;
                    System.out.println("nodeDataStart: " +nodeDataStart);
                    nodeDataStartKey=nodeDataStart.substring(nodeDataStart.indexOf("key\":-")+6, nodeDataStart.indexOf(", \"loc\""));
                    System.out.println("nodeDataStartKey: " +nodeDataStartKey);

                }else if(part.lastIndexOf("\"category\":\"End\"")>=0){
                    nodeDataEnd=part;
                    System.out.println("nodeDataEnd: " +nodeDataEnd);

                    nodeDataEndKey=nodeDataEnd.substring(nodeDataEnd.indexOf("key\":-")+6, nodeDataEnd.indexOf(", \"loc\""));
                    System.out.println("nodeDataEndKey: " +nodeDataEndKey);

                }else if(part.lastIndexOf("\"category\":\"Question\"")>=0){
                    nodeDataQuestion[nodeDataQuestionIndex++]=part;
                    System.out.println("nodeDataQuestion: " +nodeDataQuestion[nodeDataQuestionIndex-1]);

                    nodeDataQuestionKey=nodeDataQuestion[nodeDataQuestionIndex-1].substring(nodeDataQuestion[nodeDataQuestionIndex-1].indexOf("key\":-")+6, nodeDataQuestion[nodeDataQuestionIndex-1].indexOf(", \"loc\""));
                    System.out.println("nodeDataQuestionKey: " +nodeDataQuestionKey);

                    nodeDataQuestionQuestionBody=nodeDataQuestion[nodeDataQuestionIndex-1].substring(nodeDataQuestion[nodeDataQuestionIndex-1].indexOf("questionBody\":")+14, nodeDataQuestion[nodeDataQuestionIndex-1].indexOf(", \"key\""));
                    System.out.println("nodeDataQuestionQuestionBody: " +nodeDataQuestionQuestionBody);

                }else if(part.lastIndexOf("\"category\":\"Answer\"")>=0){
                    nodeDataAnswer[nodeDataAnswerIndex++]=part;
                    System.out.println("nodeDataAnswer: " +nodeDataAnswer[nodeDataAnswerIndex-1]);

                    nodeDataAnswerKey=nodeDataAnswer[nodeDataAnswerIndex-1].substring(nodeDataAnswer[nodeDataAnswerIndex-1].indexOf("key\":-")+6, nodeDataAnswer[nodeDataAnswerIndex-1].indexOf(", \"loc\""));
                    System.out.println("nodeDataAnswerKey: " +nodeDataAnswerKey);

                    nodeDataAnswerAnswerKey=nodeDataAnswer[nodeDataAnswerIndex-1].substring(nodeDataAnswer[nodeDataAnswerIndex-1].indexOf("answerKey\":")+11, nodeDataAnswer[nodeDataAnswerIndex-1].indexOf(", \"answerValue\""));
                    System.out.println("nodeDataAnswerAnswerKey: " +nodeDataAnswerAnswerKey);

                    nodeDataAnswerAnswerValue=nodeDataAnswer[nodeDataAnswerIndex-1].substring(nodeDataAnswer[nodeDataAnswerIndex-1].indexOf("answerValue\":")+13, nodeDataAnswer[nodeDataAnswerIndex-1].indexOf(", \"key\""));
                    System.out.println("nodeDataAnswerAnswerValue: " +nodeDataAnswerAnswerValue);
                }
            }else{
                System.out.println("ignore part: " +part);
            }
        }

        String linkDataArrayPart = json.substring(json.indexOf("linkDataArray")+17, json.lastIndexOf("]"));
        System.out.println("linkDataArrayPart:"+linkDataArrayPart);
        String[] linkDataArray = linkDataArrayPart.split("}");
        String linkDataFrom = "";
        String linkDataTo = "";
        for(String part : linkDataArray){
            if((null!=part) && (part.length()>5)){
                part = part.trim();
                part = part.substring(0,part.indexOf(", \"fromPort\""));
                part=part.substring(part.indexOf("{\"")+1);
                linkDataFrom = part.substring(part.indexOf("from\":-")+7,part.indexOf(", \"to\""));
                linkDataTo = part.substring(part.indexOf("to\":-")+5);
                System.out.println("part: " +part);
                System.out.println("linkDataFrom: " +linkDataFrom);
                System.out.println("linkDataTo: " +linkDataTo);
            }else{
                System.out.println("ignore part: " +part);
            }
        }

    }

}

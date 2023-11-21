package za.co.wethinkcode.toyrobot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplayCommand extends Command {

    public boolean reversed = false;
    public List<String> rangeList;
    public ArrayList<String> numbers = new ArrayList<>();
    public ArrayList<String> tempCommandList = new ArrayList<>();
    public int startNum;
    public int endNum;

    @Override
    public boolean execute(Robot target) {
//        String[] args = getArgument().toLowerCase().trim().split(" ");
//        for (String argument: Arrays.asList(args)) {
//            if(argument.matches("[\\d]{1,2}-[\\d]{1,2}")){
//                numbers.add(argument.split("-")[0]);
//                numbers.add(argument.split("-")[1]);
//                break;
//            }
//        }
//        if(Arrays.asList(args).contains("reversed")){
//            reversed = true;
//        }

        if(checkArgs(target)){
            rangeList = target.replay.subList(startNum, endNum);
        }else{
            return true;
        }
//        int a = Integer.parseInt(numbers.get(0));
//        int b = Integer.parseInt(numbers.get(1));
//        rangeList = target.replay.subList(a, b);
        target.replaying = true;
        if (reversed){
            for(int i = rangeList.size() - 1; i >= 0; i--){
                target.handleCommand(Command.create(rangeList.get(i)));
                Play.printWords(target.toString());
            }
        }else {
            for (String command : rangeList) {
                target.handleCommand(Command.create(command));
                Play.printWords(target.toString());
            }
        }
        target.setStatus("replayed " + (rangeList.size()) + " commands.");

        target.replaying = false;
        return true;
    }

    private boolean checkArgs(Robot robot){
        String[] argList = getArgument().replaceAll("[\\],]", "").toLowerCase().trim().split(" ");
        tempCommandList = robot.getReplayList();
        startNum = 0;
        endNum = tempCommandList.size();

        for (int i = 1; i < argList.length; i++) {
            if (argList[i].equals("reversed")){
                reversed = true;
            }else if(checkRange(argList[i])) {
                startNum = tempCommandList.size() - (Integer.parseInt(argList[i]));
                endNum = tempCommandList.size();
            }else if(checkRangeDouble(argList[i])){
                String[] indexes = argList[i].split("-");
                if(Integer.parseInt(indexes[0]) < Integer.parseInt(indexes[1])){
                    robot.setStatus("Invalid range");
                    return false;
                }
                startNum = tempCommandList.size() - Integer.parseInt(indexes[0]);
                endNum = tempCommandList.size() - Integer.parseInt(indexes[1]);
            }
            else{
                robot.setStatus("Invalid argument");
                return false;
            }
        }
        return true;
    }

    private boolean checkRange(String arg){
        Pattern pattern = Pattern.compile("\\d{1,2}");
        Matcher matcher = pattern.matcher(arg);
        return matcher.matches();
    }

    private boolean checkRangeDouble(String arg){
        Pattern pattern = Pattern.compile("\\d{1,2}-\\d{1,2}");
        Matcher matcher = pattern.matcher(arg);
        return matcher.matches();
    }

    public ReplayCommand(String argument) {
        super("replay", argument);
    }
}
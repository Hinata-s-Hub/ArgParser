import java.util.List;

public class Sample {
    public static void main(String[] args) {
        final ArgParser ARG = prepareArgParser(args);
        List<String> texts = ARG.getListofString("texts");
        System.out.print("texts : ");
        for(int i = 0; ; i++){
            if(i == texts.size()){
                System.out.print("\n");
                break;
            }
            System.out.printf("%s, ", texts.get(i));
        }
        System.out.printf("exist : %b\n", ARG.getBoolean("exist"));
        System.out.printf("text : %s\n", ARG.getString("text"));
        System.out.printf("num : %d\n", ARG.getInteger("num"));
    }

    public static ArgParser prepareArgParser(String[] args){
        ArgParser argument = ArgParser.getInstance();
        argument.registerListOfString("texts", null);    
        argument.registerBoolean("exist", false);    
        argument.registerString("text", null);          
        argument.registerInteger("num", 10);          
        argument.analyze(args);

        return argument;
    }
}

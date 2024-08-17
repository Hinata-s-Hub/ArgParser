import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * シングルトン実装の引数解析クラス
 */
public class ArgParser {
    private static final ArgParser INSTANCE = new ArgParser();

    private ArgParser() {}

    // 引数に登録できる型
    private enum Type {
        INTEGER, DOUBLE, LONG, BOOLEAN, STRING, LIST_STRING
    }

    // 登録した引数の名前と型を記録する
    private Map<String, Type> registration = new HashMap<>();

    // 名前に対応する値を格納する
    private Map<String, Integer> argInteger = new HashMap<>();
    private Map<String, Double> argDouble = new HashMap<>();
    private Map<String, Long> argLong = new HashMap<>();
    private Map<String, Boolean> argBoolean = new HashMap<>();
    private Map<String, String> argString = new HashMap<>();
    private Map<String, List<String>> argListOfString = new HashMap<>();

    // 引数を解析して値を更新
    public final void analyze(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String name = args[i];
            if (isVaildArg(name)) {
                name = name.substring(2, name.length()); // '--' を取り除く

                Type type = registration.get(name);
                // 先に入力候補の値がメタ文字を含んでないか
                // あるいは次のargで"out of bounds"にならないか調べる(Booleanは除く)
                if(type != Type.BOOLEAN){
                    if(i + 1 >= args.length || args[i + 1].charAt(0) == '-'){
                        System.out.printf("\u001b[00;31mcaution: ArgAnalyst \"%s\" has invalid input\u001b[00m\n", name);
                        continue;
                    }
                }
                // typeに応じて代入処理
                switch (type) {
                    case INTEGER:
                        argInteger.replace(name, Integer.valueOf(args[++i]));
                        break;
                    case DOUBLE:
                        argDouble.replace(name, Double.valueOf(args[++i]));
                        break;
                    case LONG:
                        argLong.replace(name, Long.valueOf(args[++i]));
                        break;
                    case BOOLEAN:
                        argBoolean.replace(name, true);
                        break;
                    case STRING:
                        argString.replace(name, args[++i]);
                        break;
                    case LIST_STRING:
                        String value = args[++i];
                        value = value.replaceAll("　", ""); // 全角スペースを取り除く
                        value = value.replaceAll(" ", ""); // 半角スペースを取り除く
                        String[] array_value = value.split(","); // ',' で分割
                        argListOfString.replace(name, Arrays.asList(array_value));
                        break;
                }
            }
        }
    }

    // 登録した引数とその内容を全て標準出力する
    public final void print() {
        System.out.println("Type: (name, value)");
        for (String key : registration.keySet()) {
            switch (registration.get(key)) {
                case INTEGER:
                    System.out.printf("argInteger: (%s, %d)\n", key, argInteger.get(key));
                    break;
                case DOUBLE:
                    System.out.printf("argDouble: (%s, %f)\n", key, argDouble.get(key));
                    break;
                case LONG:
                    System.out.printf("argLong: (%s, %d)\n", key, argLong.get(key));
                    break;
                case BOOLEAN:
                    System.out.printf("argBoolean: (%s, %b)\n", key, argBoolean.get(key));
                    break;
                case STRING:
                    System.out.printf("argString: (%s, %s)\n", key, argString.get(key));
                    break;
                case LIST_STRING:
                    System.out.printf("argListOfString: (%s, %s)\n", key, argListOfString.get(key).toString());
                    break;
            }
        }
    }

    // 引数の指定が有効かtrue/falseで返
    private final boolean isVaildArg(String arg) {
        if (arg.charAt(0) == '-' && arg.charAt(1) == '-') {
            String tmp = arg.substring(2, arg.length());
            if (registration.containsKey(tmp)) {
                return true;
            }
        }
        return false;
    }
    
    // インスタンスを取得する
    public static final ArgParser getInstance() {
        return INSTANCE;
    }

    // 引数を登録する
    public final void registerInteger(String name, Integer init) {
        registration.put(name, Type.INTEGER);
        argInteger.put(name, init);
    }

    public final void registerDouble(String name, Double init) {
        registration.put(name, Type.DOUBLE);
        argDouble.put(name, init);
    }

    public final void registerLong(String name, Long init) {
        registration.put(name, Type.LONG);
        argLong.put(name, init);
    }

    public final void registerBoolean(String name, Boolean init) {
        registration.put(name, Type.BOOLEAN);
        argBoolean.put(name, init);
    }

    public final void registerString(String name, String init) {
        registration.put(name, Type.STRING);
        argString.put(name, init);
    }

    public final void registerListOfString(String name, List<String> init) {
        registration.put(name, Type.LIST_STRING);
        argListOfString.put(name, init);
    }

    // 指定した名前に対応する値を返す
    public final Integer getInteger(String name) {
        return argInteger.get(name);
    }

    public final Double getDouble(String name) {
        return argDouble.get(name);
    }

    public final Long getLong(String name) {
        return argLong.get(name);
    }

    public final Boolean getBoolean(String name) {
        return argBoolean.get(name);
    }

    public final String getString(String name) {
        return argString.get(name);
    }

    public final List<String> getListofString(String name) {
        return argListOfString.get(name);
    }
}

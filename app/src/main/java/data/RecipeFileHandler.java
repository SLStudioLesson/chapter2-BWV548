package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * データ操作に関連したクラス
 */
public class RecipeFileHandler {
    private String filePath;

    /**
     * コンストラクタ
     * filePathは下の文字列
     * 
     */
    public RecipeFileHandler() {
        filePath = "app/src/main/resources/recipes.txt";
    }

    /**
     * コンストラクタ
     * filePathは引数の文字列
     * @param filePath
     */
    public RecipeFileHandler(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 設問1: 一覧表示機能
     * recipes.txtからレシピデータを読み込み、それをリスト形式で返します。 <br>
     * IOExceptionが発生したときは<i>Error reading file: 例外のメッセージ</i>とコンソールに表示します。
     *
     * @return レシピデータ
     */
    public ArrayList<String> readRecipes() {
        ArrayList<String> recipes = new ArrayList<>();
        // ファイルを読み込む
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // recipes.txtからレシピデータを1行ずつ格納する
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    recipes.add(line);
                } else {
                    // 全て読み込んだら終了
                    break;
                }
            }
            return recipes;
        } catch (IOException e) {
            // 「Error reading file: 例外のメッセージ」と表示
            System.out.println("Error reading file:" + e.getMessage());
        }
        return null;
    }

    /**
     * 設問2: 新規登録機能
     * 新しいレシピをrecipes.txtに追加します。<br>
     * レシピ名と材料はカンマ区切りで1行としてファイルに書き込まれます。
     *
     * @param recipeName レシピ名
     * @param ingredients 材料名
     */
     //
    public void addRecipe(String recipeName, String ingredients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // レシピ名と材料を結合
            String newRecipe = recipeName + "," + ingredients;
            // ファイルに書き込み
            writer.write(newRecipe);
            writer.newLine();
        } catch (IOException e) {
            // 「Error reading file: 例外のメッセージ」と表示
            System.out.println("Error reading file:" + e.getMessage());
        }
    }

    /**
     * レシピからレシピ名を取得
     * @param recipe
     * @return
     */
    public String getRecipeName(String recipe) {
        String recipeName = (recipe.split(","))[0];
        return recipeName;
    }

    /**
     * レシピから材料を取得
     * @param recipe
     * @return
     */
    public String getMainIngredients(String recipe) {
        String mainIngredients = (recipe.split(",", 2))[1];
        return mainIngredients;
    }
}

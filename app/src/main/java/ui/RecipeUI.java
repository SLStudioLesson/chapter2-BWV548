package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import data.RecipeFileHandler;

public class RecipeUI {
    private BufferedReader reader;
    private RecipeFileHandler fileHandler;

    /**
     * コンストラクタ
     */
    public RecipeUI() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        fileHandler = new RecipeFileHandler();
    }

    /**
     * コンストラクタ
     * @param reader
     * @param fileHandler
     */
    public RecipeUI(BufferedReader reader, RecipeFileHandler fileHandler) {
        this.reader = reader;
        this.fileHandler = fileHandler;
    }

    /**
     * ここで表示
     */
    public void displayMenu() {
        Outer:
        while (true) {
            try {
                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");

                String choice = reader.readLine();

                // choiceの値に応じて処理を分岐
                switch (choice) {
                    case "1":
                        // 設問1: 一覧表示機能
                        displayRecipes();
                        break Outer;
                    case "2":
                        // 設問2: 新規登録機能
                        addNewRecipe();
                        break Outer;
                    case "3":
                        // 設問3: 検索機能
                        searchRecipe();
                        break Outer;
                    case "4":
                        System.out.println("Exit the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading input from user: " + e.getMessage());
            }
        }
    }

    /**
     * 設問1: 一覧表示機能
     * RecipeFileHandlerから読み込んだレシピデータを整形してコンソールに表示します。
     */
    private void displayRecipes() {
        // readRecipesからレシピの配列を取得
        ArrayList<String> recipes = fileHandler.readRecipes();
        // recipesで1行でも読み込めていれば（recipes.txtが空でなければ）、読み込んだデータをリスト形式で返す。
        if (recipes.size() != 0) {
        String recipeName = ""; // レシピ名
        String mainIngredients = ""; // 材料
        System.out.println();   // 改行
        System.out.println("Recipes:");
        System.out.println("-----------------------------------");
        for (String recipe : recipes) {
            recipeName = fileHandler.getRecipeName(recipe);
            mainIngredients = fileHandler.getMainIngredients(recipe);
            System.out.println("Recipe Name: " + recipeName);
            System.out.println("Main Ingredients: " + mainIngredients);
            System.out.println("-----------------------------------");
        }
        // recipes.txtがからなら、メッセージのみ出力
        } else {
            System.out.println("No recipes available.");
        }
    }

    /**
     * 設問2: 新規登録機能
     * ユーザーからレシピ名と主な材料を入力させ、RecipeFileHandlerを使用してrecipes.txtに新しいレシピを追加します。
     *
     * @throws java.io.IOException 入出力が受け付けられない
     */
    private void addNewRecipe() throws IOException {
        // レシピ名の入力
        System.out.println();   // 改行
        System.out.print("Enter recipe name: ");
        String inputRecipeName = reader.readLine();
        // 材料の入力
        System.out.print("Enter main ingredients (comma separated): ");
        String inputMainIngredients = reader.readLine();
        // 入力内容をaddRecipeに渡してファイルに追記
        fileHandler.addRecipe(inputRecipeName, inputMainIngredients);
        System.out.println("Recipe added successfully.");
    }

    /**
     * 設問3: 検索機能
     * ユーザーから検索クエリを入力させ、そのクエリに基づいてレシピを検索し、一致するレシピをコンソールに表示します。
     *
     * @throws java.io.IOException 入出力が受け付けられない
     */
    private void searchRecipe() throws IOException {
        System.out.print("Enter search query (e.g., 'name=Tomato&ingredient=Garlic'): ");
        String inputQuery = reader.readLine();
        String recipePart = ""; // レシピ部分
        String mainIngredientPart = ""; // 材料部分

        // レシピ名と材料名の両方が入力された場合
        if (inputQuery.indexOf("&") >= 0) {
            // レシピ名部分と材料部分に分割
            recipePart = inputQuery.split("&")[0];
            mainIngredientPart = inputQuery.split("&")[1];
        // レシピ名のみ入力された場合
        } else if (inputQuery.indexOf("name") >= 0) {
            recipePart = inputQuery;
        // 材料のみ入力された場合
        } else if (inputQuery.indexOf("ingredient") >= 0) {
            mainIngredientPart = inputQuery;
        }
        // レシピと材料に分割
        String inputRecipe = recipePart.substring(recipePart.indexOf("=") + 1);
        String inputMainIngredient = mainIngredientPart.substring(mainIngredientPart.indexOf("=") + 1);
        
        // 結果の出力を開始
        int count = 0;  // 一致するレシピがあった場合に加算
        System.out.println();   // 改行
        System.out.println("Search Results:");
        // 入力からレシピ名と材料を検索
        // レシピを取得
        ArrayList<String> recipes = fileHandler.readRecipes();
        for (String recipe : recipes) {
            // レシピ名が含まれ、かつ材料が含まれる場合（indexOfが-1でない場合）はtrue
            // 片方しか入力がなかった場合、もう片方は自動的にtrueとなる
            if (fileHandler.getRecipeName(recipe).indexOf(inputRecipe) >= 0 &&
                    fileHandler.getMainIngredients(recipe).indexOf(inputMainIngredient) >= 0) {
                System.out.println(recipe);
                count++;
            }
        }
        // 一度も一致しなかった場合のメッセージ
        if (count == 0) {
            System.out.println("No recipes found matching the criteria.");
        }
    }
}


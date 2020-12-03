package gameEngine;

import commands.Command;
import commands.CommandWord;
import commands.Parser;
import item.Book;
import item.Item;
import player.Player;

import java.util.List;

public class Child extends Game {
    public Child(Player p1, Parser parser) {
        super(p1, parser, 60);
    }

    public void play() {
        parser = new Parser();
        Command command = parser.getCommand();
        processCommand(command);
    }

    public boolean processCommand(Command command) {
        CommandWord commandWord = command.getCommandWord();
        switch(commandWord) {
            case READ -> readBook();
            case WORK -> work(7);
            default -> super.processCommand(command);
        }
        return true;
    }

    // Read the first book in your inventory, if any
    public void readBook() {
        if(!inPlace("school"))
            return;

        List<Item> inventory = super.getPlayer().getInventory();
        boolean hasBook = false;
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            if (item instanceof Book) {
                hasBook = true;
                int bookKnowledgePoints = ((Book)item).getKNOWLEDGEPOINTS();
                if(getPlayer().getSickness() != null){
                    bookKnowledgePoints = bookKnowledgePoints - 50;
                    System.out.println("You can't study while sick");
                }
                super.getPlayer().incKnowledge(bookKnowledgePoints);
                super.getPlayer().removeInventoryItem(item);
                System.out.println("You read a book that gave you " + bookKnowledgePoints + " knowledge points");
                super.turns.decTurns(bookKnowledgePoints / 50);
                super.checkTurns();
                break;
            }
        }
        if (!hasBook) {
            System.out.println("You do not have a book in your inventory");
        }
    }
}

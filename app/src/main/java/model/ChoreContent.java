package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoreContent {

    public static final List<Chore> ITEMS = new ArrayList<Chore>();

    public static final Map<String, Chore> ITEM_MAP = new HashMap<String, Chore>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createChoreItem(i));
        }
    }

    private static void addItem(Chore item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getmChore(), item);
    }

    private static Chore createChoreItem(int position) {
        return new Chore("Chore" + position, "Addr" + position ,"long desc" + position
                , "Price" + position, "Email" + position);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Chore: ").append(position);
        for (int i = 0; i < position; i++ ) {
            builder.append("\nMore details here.");
        }
        return builder.toString();
    }

    /**
     * An equipment item representing a piece of content.
     */
    public static class ChoreItem {
        public final String name;
//        public final String addr;
        public final String details;
        public final String price;
//        public final String email;

        public ChoreItem(String name, String addr, String price) {
            this.name = name;
            this.details = addr;
            this.price = price;
        }

        @Override
        public String toString() {
            return details;
        }
    }


}

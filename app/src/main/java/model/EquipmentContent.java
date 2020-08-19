package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipmentContent {

    public static final List<Equipment> ITEMS = new ArrayList<Equipment>();

    public static final Map<String, Equipment> ITEM_MAP = new HashMap<String, Equipment>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createEquipmentItem(i));
        }
    }

    private static void addItem(Equipment item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getmEquipmentEquipment(), item);
    }

    private static Equipment createEquipmentItem(int position) {
        return new Equipment("Equipment" + position, "short desc" + position
                , "Price" + position, "Email" + position);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Equipment: ").append(position);
        for (int i = 0; i < position; i++ ) {
            builder.append("\nMore details here.");
        }
        return builder.toString();
    }

    /**
     * An equipment item representing a piece of content.
     */
    public static class EquipmentItem {
        public final String name;
        public final String details;
        public final String price;

        public EquipmentItem(String name, String details, String price) {
            this.name = name;
            this.details = details;
            this.price = price;
        }

        @Override
        public String toString() {
            return details;
        }
    }

}

package com.gildedrose;

import java.util.Arrays;
import java.util.function.Function;

import static com.gildedrose.GildedRose.TYPE.GENERAL;

class GildedRose {
    Item[] items;

    enum TYPE {
        GENERAL("GENERAL"),
        AGED_BRIE("Aged Brie") {
            @Override
            protected int getAddition(int sellIn) {
                return sellIn < 0 ? 2 : 1;
            }

            @Override
            protected Function<Integer, Integer> getLimitFunction() {
                return q -> Math.min(q, 50);
            }
        },
        SULFURAS("Sulfuras, Hand of Ragnaros") {
            @Override
            public int newSellIn(int sellIn) {
                return sellIn;
            }

            @Override
            public int newQuality(int sellIn, int quality) {
                return quality;
            }
        },
        BACKSTAGE("Backstage passes to a TAFKAL80ETC concert") {
            @Override
            protected int getAddition(int sellIn) {
                if (sellIn < 5) return 3;
                else if (sellIn < 10) return 2;
                else return 1;
            }

            @Override
            public int newQuality(final int sellIn, final int quality) {
                if (sellIn < 0) return 0;
                if (quality >= 50) return quality;

                return super.newQuality(sellIn, quality);
            }
        };

        private final String name;

        TYPE(String name) {
            this.name = name;
        }

        protected int getAddition(int sellIn) {
            return sellIn < 0 ? -2 : -1;
        }

        protected Function<Integer, Integer> getLimitFunction() {
            return q -> Math.max(q, 0);
        }

        public int newSellIn(int sellIn) {
            return sellIn - 1;
        }

        public int newQuality(final int sellIn, final int quality) {
            return getLimitFunction().apply(quality + getAddition(sellIn));
        }
    }

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items)
            .forEach(this::updateItemQuality);
    }

    private void updateItemQuality(Item item) {
        item.sellIn = getTypeOf(item).newSellIn(item.sellIn);
        item.quality = getTypeOf(item).newQuality(item.sellIn, item.quality);
    }

    private TYPE getTypeOf(Item item) {
        return Arrays.stream(TYPE.values())
            .filter(t -> t.name.equals(item.name))
            .findFirst()
            .orElse(GENERAL);
    }
}

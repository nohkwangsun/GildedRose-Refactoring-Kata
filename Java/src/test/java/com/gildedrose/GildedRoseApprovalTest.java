package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.lambda.utils.Range;

import static org.approvaltests.combinations.CombinationApprovals.verifyAllCombinations;

class GildedRoseApprovalTest {

    @Test
    void updateQuality() {
        String[] names = {"foo", "Aged Brie", "Sulfuras, Hand of Ragnaros", "Backstage passes to a TAFKAL80ETC concert"};
        Integer[] sellIns = Range.get(-10, 60);
        Integer[] qualities = Range.get(-10, 60);
        verifyAllCombinations(this::updateQualityForItem, names, sellIns, qualities);

    }

    private String updateQualityForItem(String name, int sellIn, int quality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return items[0].toString();
    }
}

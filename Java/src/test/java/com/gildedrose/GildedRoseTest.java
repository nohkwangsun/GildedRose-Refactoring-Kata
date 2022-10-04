package com.gildedrose;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo, -1, 0", app.items[0].toString());
    }

    @Test
    void 하루가_지날때마다_시스템은_SellIn_Quality을_1씩_감소() {
        Item[] items = {
            new Item("foo", 100, 100),
            new Item("bar", 10, 10),
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(99, app.items[0].sellIn);
        assertEquals(99, app.items[0].quality);
        assertEquals(9, app.items[1].sellIn);
        assertEquals(9, app.items[1].quality);
    }

    @Test
    void 이틀_지나면_시스템은_SellIn_Quality을_1씩_두번_감소() {
        Item[] items = {
            new Item("foo", 100, 60),
            new Item("bar", 10, 6),
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        app.updateQuality();
        assertEquals(98, app.items[0].sellIn);
        assertEquals(58, app.items[0].quality);
        assertEquals(8, app.items[1].sellIn);
        assertEquals(4, app.items[1].quality);
    }

    @Test
    void 판매하는_나머지_일수가_없어지면_Quality는_2배로_떨어집니다_단_음수는_되지않음() {
        Item[] items = {
            new Item("foo", 5, 3),
            new Item("bar", 10, 15),
            new Item("sam", 5, 15),
        };
        GildedRose app = new GildedRose(items);
        IntStream.range(0, 8).forEach(i -> app.updateQuality());
        assertEquals(-3, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
        assertEquals(2, app.items[1].sellIn);
        assertEquals(7, app.items[1].quality);
        assertEquals(-3, app.items[2].sellIn);
        assertEquals(4, app.items[2].quality);
    }

    @Test
    void Aged_Brie는_qualiy가_올라감() {
        Item[] items = {
            new Item("Aged Brie", 10, 3),
        };
        GildedRose app = new GildedRose(items);
        IntStream.range(0, 5).forEach(i -> app.updateQuality());
        assertEquals(5, app.items[0].sellIn);
        assertEquals(8, app.items[0].quality);
    }

    @Test
    void Aged_Brie는_sellIn_없을때_qualiy가_두배로_올라감() {
        Item[] items = {
            new Item("Aged Brie", 10, 3),
        };
        GildedRose app = new GildedRose(items);
        IntStream.range(0, 20).forEach(i -> app.updateQuality());
        assertEquals(-10, app.items[0].sellIn);
        assertEquals(33, app.items[0].quality);
    }

    @Test
    void Aged_Brie는_qualiy_50초과_불가() {
        Item[] items = {
            new Item("Aged Brie", 40, 40),
            new Item("Backstage passes to a TAFKAL80ETC concert", 40, 45),
        };
        GildedRose app = new GildedRose(items);
        IntStream.range(0, 30).forEach(i -> app.updateQuality());
        assertEquals(10, app.items[0].sellIn);
        assertEquals(50, app.items[0].quality);
        assertEquals(10, app.items[1].sellIn);
        assertEquals(50, app.items[1].quality);
    }

    @Test
    void Sulfuras는_SellIn과_Quality가_내려가지_않음() {
        Item[] items = {
            new Item("Sulfuras, Hand of Ragnaros", 40, 40),
        };
        GildedRose app = new GildedRose(items);
        IntStream.range(0, 30).forEach(i -> app.updateQuality());
        assertEquals(40, app.items[0].sellIn);
        assertEquals(40, app.items[0].quality);
    }
    @Test
    void Sulfuras는_Quality가_80임() {
        Item[] items = {
            new Item("Sulfuras, Hand of Ragnaros", 40, 80),
        };
        GildedRose app = new GildedRose(items);
        IntStream.range(0, 30).forEach(i -> app.updateQuality());
        assertEquals(40, app.items[0].sellIn);
        assertEquals(80, app.items[0].quality);
    }

    @Test
    void Backstage는_SellIn에_가까워_질수록_증가_10일전까지는_2씩_5일전까지는_3씩() {
        Item[] items = {
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20),
        };
        GildedRose app = new GildedRose(items);
        IntStream.range(0, 5).forEach(i -> app.updateQuality());
        assertEquals(10, app.items[0].sellIn);
        assertEquals(25, app.items[0].quality);
        assertEquals(5, app.items[1].sellIn);
        assertEquals(30, app.items[1].quality);
        assertEquals(0, app.items[2].sellIn);
        assertEquals(35, app.items[2].quality);
    }

    @Test
    void Backstage는_SellIn_지나면_Quality가_0() {
        Item[] items = {
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 4, 20),
        };
        GildedRose app = new GildedRose(items);
        IntStream.range(0, 5).forEach(i -> app.updateQuality());
        assertEquals(0, app.items[0].sellIn);
        assertEquals(35, app.items[0].quality);
        assertEquals(-1, app.items[1].sellIn);
        assertEquals(0, app.items[1].quality);
    }

//    @Test
    void Conjured는_2배_속도_Quality_저하() {
        Item[] items = {
            new Item("Conjured", 5, 40),
            new Item("Conjured", 0, 40),
        };
        GildedRose app = new GildedRose(items);
        IntStream.range(0, 5).forEach(i -> app.updateQuality());
        assertEquals(0, app.items[0].sellIn);
        assertEquals(30, app.items[0].quality);
        assertEquals(-5, app.items[1].sellIn);
        assertEquals(20, app.items[1].quality);
    }
}

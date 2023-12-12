package com.gildedrose;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Nested
    class StandardItems {
        @Test
        void decreaseQualityAndSellIn() {
            Item standardItem1 = new Item("item1", 100, 30);
            Item standardItem2 = new Item("item2", 50, 50);
            Item[] items = new Item[]{standardItem1, standardItem2};
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals(app.items[0].quality, 29);
            assertEquals(app.items[0].sellIn, 99);
            assertEquals(app.items[1].quality, 49);
            assertEquals(app.items[1].sellIn, 49);
        }

        @Test
        void decreaseQualityByTwoAfterExpiration() {
            Item expiredStandardItem = new Item("item1", 0, 30);
            Item[] items = new Item[]{expiredStandardItem};
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals(app.items[0].quality, 28);
        }

        @Test
        void neverGoBelow0() {
            Item[] items = new Item[]{
                new Item("item1", 0, 0),
                new Item("item2", 10, 0),
                new Item("item2", 0, 1),
            };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals(app.items[0].quality, 0);
            assertEquals(app.items[1].quality, 0);
            assertEquals(app.items[2].quality, 0);
        }
    }

    @Nested
    class AgedBries {
        @Test
        void increaseQualityWhenAging() {
            Item[] items = new Item[]{
                new Item("Aged Brie", 9, 0),
                new Item("Aged Brie", 0, 0)
            };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals(app.items[0].quality, 1);
            assertEquals(app.items[1].quality, 2);
        }

        @Test
        void neverGoAbove50() {
            Item[] items = new Item[]{
                new Item("Aged Brie", 9, 50),
                new Item("Aged Brie", 0, 50),
                new Item("Aged Brie", 0, 49)
            };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals(app.items[0].quality, 50);
            assertEquals(app.items[1].quality, 50);
            assertEquals(app.items[2].quality, 50);
        }
    }

    @Nested
    class Sulfuras {
        @Test
        void shouldNeverDecreaseQuality() {
            //TODO: Sulfuras should be protected against instantiation with quality != 80
            Item[] items = new Item[]{
                new Item("Sulfuras, Hand of Ragnaros", 100, 80)
            };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals(app.items[0].quality, 80);
            assertEquals(app.items[0].sellIn, 100);
        }
    }

    @Nested
    class BackStagePasses {
        @Test
        void increaseValueByOneWhenSellInIsGreaterThan10() {
            Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 11, 0),
                new Item("Backstage passes to a TAFKAL80ETC concert", 100, 0)
            };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals(app.items[0].quality, 1);
            assertEquals(app.items[1].quality, 1);
        }

        @Test
        void increaseValueByTwoWhenSellInIsBetween5And10() {
            Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 0),
                new Item("Backstage passes to a TAFKAL80ETC concert", 6, 0)
            };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals(app.items[0].quality, 2);
            assertEquals(app.items[1].quality, 2);
        }

        @Test
        void increaseValueByThreeWhenSellInIsBetween0And5() {
            Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 0),
                new Item("Backstage passes to a TAFKAL80ETC concert", 1, 0)
            };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals(app.items[0].quality, 3);
            assertEquals(app.items[1].quality, 3);
        }

        @Test
        void dropValueToZeroAfterConcert() {
            Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 0, 50),
                new Item("Backstage passes to a TAFKAL80ETC concert", -1, 50)
            };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals(app.items[0].quality, 0);
            assertEquals(app.items[1].quality, 0);
        }
    }
}

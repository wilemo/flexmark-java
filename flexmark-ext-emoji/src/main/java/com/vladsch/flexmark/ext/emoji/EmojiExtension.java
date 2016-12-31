package com.vladsch.flexmark.ext.emoji;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.emoji.internal.EmojiDelimiterProcessor;
import com.vladsch.flexmark.ext.emoji.internal.EmojiJiraRenderer;
import com.vladsch.flexmark.ext.emoji.internal.EmojiNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for emoji shortcuts using Emoji-Cheat-Sheet.com.
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed emoji shortcuts text regions are turned into {@link Emoji} nodes.
 * </p>
 */
public class EmojiExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public static final DataKey<String> ATTR_ALIGN = new DataKey<>("ATTR_ALIGN", "absmiddle");
    public static final DataKey<String> ATTR_IMAGE_SIZE = new DataKey<>("ATTR_IMAGE_SIZE", "20");
    public static final DataKey<String> ROOT_IMAGE_PATH = new DataKey<>("ROOT_IMAGE_PATH", "/img/");
    public static final DataKey<Boolean> USE_IMAGE_URLS = new DataKey<>("USE_IMAGE_URLS", false);

    private EmojiExtension() {
    }

    public static Extension create() {
        return new EmojiExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new EmojiDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        switch (rendererType) {
            case "HTML":
                rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
                    @Override
                    public NodeRenderer create(DataHolder options) {
                        return new EmojiNodeRenderer(options);
                    }
                });
                break;
            case "JIRA":
            case "YOUTRACK":
                rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
                    @Override
                    public NodeRenderer create(DataHolder options) {
                        return new EmojiJiraRenderer(options);
                    }
                });
                break;
        }
    }
}

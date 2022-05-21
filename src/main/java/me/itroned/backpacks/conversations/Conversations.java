package me.itroned.backpacks.conversations;

import me.itroned.backpacks.Backpacks;
import org.bukkit.conversations.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Conversations {
    static final Prompt renamePrompt = new Prompt() {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return "§l§cWrite a new name here (Standard text formatting)";
        }

        @Override
        public boolean blocksForInput(@NotNull ConversationContext context) {
            return true;
        }

        @Nullable
        @Override
        public Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
            return null;
        }
    };
    public static Prompt getRenamePrompt(){
        return renamePrompt;
    }


}

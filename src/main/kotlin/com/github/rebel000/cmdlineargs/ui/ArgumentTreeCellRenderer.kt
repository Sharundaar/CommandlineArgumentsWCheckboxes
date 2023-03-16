package com.github.rebel000.cmdlineargs.ui

import com.intellij.icons.AllIcons
import com.intellij.ui.CheckboxTree
import com.intellij.ui.SimpleTextAttributes
import javax.swing.JTree

class ArgumentTreeCellRenderer : CheckboxTree.CheckboxTreeCellRenderer() {
    override fun customizeRenderer(
        tree: JTree,
        value: Any,
        selected: Boolean,
        expanded: Boolean,
        leaf: Boolean,
        row: Int,
        hasFocus: Boolean
    ) {
        if (value is ArgumentTreeNode) {
            var padding = 5
            if (value.name.length < 100) {
                padding = 100
            }
            if (value.isFolder) {
                textRenderer.preferredSize
                textRenderer.icon = AllIcons.Nodes.Folder
                textRenderer.append(String.format("[%s]  ", value.name))
                textRenderer.append(value.filters.toString(), SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES)
                textRenderer.appendTextPadding(padding)
            } else {
                textRenderer.append(value.name)
                textRenderer.append(value.filters.toString(), SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES)
                textRenderer.appendTextPadding(padding)
            }
        }
    }
}
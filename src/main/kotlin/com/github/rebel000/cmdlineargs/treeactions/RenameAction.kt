package com.github.rebel000.cmdlineargs.treeactions

import com.github.rebel000.cmdlineargs.ui.ArgumentTree
import com.intellij.openapi.actionSystem.AnActionEvent
import javax.swing.tree.TreePath

class RenameAction : TreeActionBase() {
    override fun actionPerformed(e: AnActionEvent) {
        val tree = ArgumentTree.getInstance(e.project) ?: return
        if (tree.isEditing) {
            tree.stopEditing()
        }
        else {
            val node = tree.selectedNode() ?: return
            val path = TreePath(node.path)
            tree.selectionPaths = arrayOf(path)
            tree.startEditingAtPath(path)
        }
    }
}
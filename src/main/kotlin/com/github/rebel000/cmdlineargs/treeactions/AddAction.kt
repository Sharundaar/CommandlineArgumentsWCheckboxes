package com.github.rebel000.cmdlineargs.treeactions

import com.github.rebel000.cmdlineargs.ui.ArgumentTree
import com.github.rebel000.cmdlineargs.ui.ArgumentTreeNode
import com.intellij.openapi.actionSystem.AnActionEvent
import javax.swing.tree.TreePath

class AddAction : TreeActionBase() {
    override fun actionPerformed(e: AnActionEvent) {
        val tree = ArgumentTree.getInstance(e.project) ?: return
        val newNode = ArgumentTreeNode("", isFolder = false, readonly = false)
        tree.addNode(newNode, tree.selectedNode())
        if (tree.isEditing) {
            tree.stopEditing()
        }
        val path = TreePath(newNode.path)
        tree.selectionPaths = arrayOf(path)
        tree.startEditingAtPath(path)
    }
}
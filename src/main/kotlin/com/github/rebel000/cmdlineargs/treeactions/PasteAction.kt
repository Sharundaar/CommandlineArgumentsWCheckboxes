package com.github.rebel000.cmdlineargs.treeactions

import com.github.rebel000.cmdlineargs.ui.ArgumentTree
import com.github.rebel000.cmdlineargs.ui.ArgumentTreeNode
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ide.CopyPasteManager
import java.awt.datatransfer.DataFlavor
import kotlin.math.max

class PasteAction : TreeActionBase() {
    override fun actionPerformed(e: AnActionEvent) {
        val tree = ArgumentTree.getInstance(e.project) ?: return
        if (tree.isEditing) return
        val str = CopyPasteManager.getInstance().getContents<String?>(DataFlavor.stringFlavor)
        if (str != null) {
            val args = str.split("\n")
            val indentSize = getIndentSize(args)
            if (args.isNotEmpty()) {
                val (baseParent, baseIndex) = tree.getInsertPosition(tree.selectedNode())
                val baseIndent = getIndent(args[0], indentSize)
                var index = baseIndex
                var depth = 0
                var indent = baseIndent
                var parent = baseParent
                var node: ArgumentTreeNode? = null
                for (arg in args) {
                    val currentIndent = max(getIndent(arg, indentSize), baseIndent)
                    if (currentIndent != indent) {
                        if (currentIndent > indent) {
                            if (node != null) {
                                node.isFolder = true
                                parent = node
                                indent = currentIndent
                                depth++
                                index = 0
                                tree.expandNode(parent)
                            }
                        } else {
                            while (currentIndent < indent) {
                                val p = parent.parent as ArgumentTreeNode
                                index = p.getIndex(parent) + 1
                                parent = p
                                indent--
                                depth--
                            }
                        }
                    }

                    node = ArgumentTreeNode(arg.trimStart(), isFolder = false, readonly = false)
                    tree.insertNode(node, parent, index++)
                    if (parent.singleChoice) {
                        tree.setNodeState(node, false)
                    }
                }
                tree.expandNode(baseParent)
            }
        }
    }

    private fun getIndentSize(args: List<String>): Int {
        for (arg in args) {
            if (arg.startsWith(' ')) {
                var i = 0
                while (i < arg.length && arg[i] == ' ') i++
                return i
            } else if (arg.startsWith('\t')) {
                return 1
            }
        }

        return 1
    }

    private fun getIndent(str: String, indentSize: Int): Int {
        var i = 0
        if (str[0] == ' ') {
            while (i < str.length && str[i] == ' ') i++
        } else {
            while (i < str.length && str[i] == '\t') i++
        }
        return i / indentSize
    }
}
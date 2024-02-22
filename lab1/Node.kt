class Node(
    var key: Int,
    var left: Node? = null,
    var right: Node? = null) {

    fun find(value: Int): Node? = when {
        this.key > value -> left?.find(value)
        this.key < value -> right?.find(value)
        else -> this
    }

    fun insert(value: Int) {
        if (value > this.key) {
            if (this.right == null) {
                this.right = Node(value)
            } else {
                this.right?.insert(value)
            }
        } else if (value < this.key) {
            if (this.left == null) {
                this.left = Node(value)
            } else {
                this.left?.insert(value)
            }
        }
    }

    fun delete(value: Int) {
        when {
            value > key -> scan(value, this.right, this)
            value < key -> scan(value, this.left, this)
            else -> removeNode(this, null)
        }
    }

    private fun removeNoChildNode(node: Node, parent: Node?) {
        parent?.let { p ->
            if (node == p.left) {
                p.left = null
            } else if (node == p.right) {
                p.right = null
            }
        } ?: throw IllegalStateException(
            "Can not remove the root node without child nodes")

    }

    private fun removeSingleChildNode(parent: Node, child: Node) {
        parent.key = child.key
        parent.left = child.left
        parent.right = child.right
    }

    private fun removeTwoChildNode(node: Node) {
        val leftChild = node.left!!
        leftChild.right?.let {
            val maxParent = findParentOfMaxChild(leftChild)
            maxParent.right?.let {
                node.key = it.key
                maxParent.right = null
            } ?: throw IllegalStateException("Node with max child must have the right child!")

        } ?: run {
            node.key = leftChild.key
            node.left = leftChild.left
        }

    }

    fun visit(): Array<Int> {
        val a = left?.visit() ?: emptyArray()
        val b = right?.visit() ?: emptyArray()
        return a + arrayOf(key) + b
    }

    /**
     * Scan the tree in the search of the given value.
     * @param value
     * @param node sub-tree that potentially might contain the sought value
     * @param parent node's parent
     */
    private fun scan(value: Int, node: Node?, parent: Node?) {
        if (node == null) {
            System.out.println("value " + value
                    + " seems not present in the tree.")
            return
        }
        when {
            value > node.key -> scan(value, node.right, node)
            value < node.key -> scan(value, node.left, node)
            value == node.key -> removeNode(node, parent)
        }

    }

    /**
     * Remove the node.
     *
     * Removal process depends on how many children the node has.
     *
     * @param node node that is to be removed
     * @param parent parent of the node to be removed
     */
    private fun removeNode(node: Node, parent: Node?) {
        node.left?.let { leftChild ->
            run {
                node.right?.let {
                    removeTwoChildNode(node)
                } ?: removeSingleChildNode(node, leftChild)
            }
        } ?: run {
            node.right?.let { rightChild -> removeSingleChildNode(node, rightChild) } ?: removeNoChildNode(node, parent)
        }


    }


    /**
     * Return a node whose right child contains the biggest value in the given sub-tree.
     * Assume that the node n has a non-null right child.
     *
     * @param n
     */
    private fun findParentOfMaxChild(n: Node): Node {
        return n.right?.let { r -> r.right?.let { findParentOfMaxChild(r) } ?: n }
            ?: throw IllegalArgumentException("Right child must be non-null")

    }

    override fun toString(): String {
        return buildString {
            append(this@Node.key.toString())
            append('\n')
            append(left?.toString().orEmpty())
            append(right?.toString().orEmpty())
            append('\n')
        }
    }
}
package org.jetbrains.kotlinx.jupyter.rdkit

import org.jetbrains.kotlinx.jupyter.api.annotations.JupyterLibrary
import org.jetbrains.kotlinx.jupyter.api.libraries.JupyterIntegration

@JupyterLibrary
class Integration: JupyterIntegration() {
    override fun Builder.onLoaded() {
        import("org.RDKit.RWMol.MolFromSmiles")

        onLoaded {
            Loader.load()
        }
    }
}

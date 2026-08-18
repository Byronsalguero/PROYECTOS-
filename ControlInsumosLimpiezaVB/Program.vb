Imports System
Imports System.Windows.Forms

Module Program
    <STAThread>
    Sub Main()
        ApplicationConfiguration.Initialize()
        Application.Run(New FrmInsumos())
    End Sub
End Module

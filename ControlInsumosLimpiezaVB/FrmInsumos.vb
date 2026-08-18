Imports System.Data
Imports System.Drawing
Imports System.Windows.Forms

Public Class FrmInsumos
    Private inventario As New DataTable()
    Private entregas As New DataTable()
    Private dgvInventario As New DataGridView()
    Private dgvEntregas As New DataGridView()
    Private txtProducto As New TextBox()
    Private txtDescripcion As New TextBox()
    Private txtExistencia As New TextBox()
    Private txtCantidad As New TextBox()
    Private txtPersona As New TextBox()
    Private txtArea As New TextBox()
    Private txtBuscar As New TextBox()
    Private dtpFecha As New DateTimePicker()
    Private lblTotal As New Label()

    Public Sub New()
        Text = "Control de Insumos de Limpieza - Municipalidad"
        Width = 1100 : Height = 700 : StartPosition = FormStartPosition.CenterScreen
        BackColor = Color.FromArgb(245, 247, 250)
        CrearTablas()
        CrearInterfaz()
    End Sub

    Private Sub CrearTablas()
        inventario.Columns.Add("Producto")
        inventario.Columns.Add("Descripción")
        inventario.Columns.Add("Existencias", GetType(Integer))
        entregas.Columns.Add("Fecha", GetType(Date))
        entregas.Columns.Add("Persona")
        entregas.Columns.Add("Área")
        entregas.Columns.Add("Producto")
        entregas.Columns.Add("Cantidad", GetType(Integer))
    End Sub

    Private Sub CrearInterfaz()
        Dim tabs As New TabControl With {.Dock = DockStyle.Fill}
        Dim tabInv As New TabPage("Inventario")
        Dim tabEnt As New TabPage("Entregas")
        tabs.TabPages.Add(tabInv) : tabs.TabPages.Add(tabEnt)
        Controls.Add(tabs)

        ' INVENTARIO
        Dim pInv As New Panel With {.Dock = DockStyle.Top, .Height = 135, .Padding = New Padding(15)}
        tabInv.Controls.Add(pInv)
        AgregarEtiqueta(pInv, "Producto:", 15, 15) : txtProducto.SetBounds(100, 12, 220, 25) : pInv.Controls.Add(txtProducto)
        AgregarEtiqueta(pInv, "Descripción:", 350, 15) : txtDescripcion.SetBounds(445, 12, 260, 25) : pInv.Controls.Add(txtDescripcion)
        AgregarEtiqueta(pInv, "Cantidad:", 15, 55) : txtExistencia.SetBounds(100, 52, 120, 25) : pInv.Controls.Add(txtExistencia)
        Dim btnAgregar As New Button With {.Text = "Agregar / Actualizar", .Bounds = New Rectangle(250, 50, 180, 32)} : AddHandler btnAgregar.Click, AddressOf AgregarInventario : pInv.Controls.Add(btnAgregar)
        Dim btnEliminar As New Button With {.Text = "Eliminar producto", .Bounds = New Rectangle(445, 50, 160, 32)} : AddHandler btnEliminar.Click, AddressOf EliminarInventario : pInv.Controls.Add(btnEliminar)
        dgvInventario.Dock = DockStyle.Fill : dgvInventario.AutoGenerateColumns = True : dgvInventario.DataSource = inventario : tabInv.Controls.Add(dgvInventario)
        lblTotal.Text = "Total de unidades en existencia: 0" : lblTotal.Dock = DockStyle.Bottom : lblTotal.Height = 35 : lblTotal.TextAlign = ContentAlignment.MiddleCenter : tabInv.Controls.Add(lblTotal)

        ' ENTREGAS
        Dim pEnt As New Panel With {.Dock = DockStyle.Top, .Height = 190, .Padding = New Padding(15)}
        tabEnt.Controls.Add(pEnt)
        AgregarEtiqueta(pEnt, "Fecha:", 15, 15) : dtpFecha.SetBounds(100, 12, 150, 25) : pEnt.Controls.Add(dtpFecha)
        AgregarEtiqueta(pEnt, "Persona recibe:", 275, 15) : txtPersona.SetBounds(380, 12, 220, 25) : pEnt.Controls.Add(txtPersona)
        AgregarEtiqueta(pEnt, "Área:", 625, 15) : txtArea.SetBounds(680, 12, 200, 25) : pEnt.Controls.Add(txtArea)
        AgregarEtiqueta(pEnt, "Producto:", 15, 55) : txtBuscar.SetBounds(100, 52, 300, 25) : pEnt.Controls.Add(txtBuscar)
        AgregarEtiqueta(pEnt, "Cantidad:", 425, 55) : txtCantidad.SetBounds(500, 52, 100, 25) : pEnt.Controls.Add(txtCantidad)
        Dim btnEntregar As New Button With {.Text = "Registrar entrega", .Bounds = New Rectangle(625, 48, 160, 32)} : AddHandler btnEntregar.Click, AddressOf RegistrarEntrega : pEnt.Controls.Add(btnEntregar)
        Dim btnReporte As New Button With {.Text = "Resumen de entregas", .Bounds = New Rectangle(800, 48, 170, 32)} : AddHandler btnReporte.Click, AddressOf MostrarResumen : pEnt.Controls.Add(btnReporte)
        dgvEntregas.Dock = DockStyle.Fill : dgvEntregas.DataSource = entregas : tabEnt.Controls.Add(dgvEntregas)
    End Sub

    Private Sub AgregarEtiqueta(p As Panel, texto As String, x As Integer, y As Integer)
        Dim l As New Label With {.Text = texto, .AutoSize = True, .Location = New Point(x, y + 4)}
        p.Controls.Add(l)
    End Sub

    Private Sub AgregarInventario(sender As Object, e As EventArgs)
        Dim producto = txtProducto.Text.Trim()
        Dim descripcion = txtDescripcion.Text.Trim()
        Dim cantidad As Integer
        If producto = "" OrElse Not Integer.TryParse(txtExistencia.Text, cantidad) OrElse cantidad < 0 Then
            MessageBox.Show("Ingrese producto y una cantidad válida.", "Datos inválidos", MessageBoxButtons.OK, MessageBoxIcon.Warning) : Return
        End If
        Dim filas = inventario.Select("Producto = '" & producto.Replace("'", "''") & "'")
        If filas.Length > 0 Then
            filas(0)("Descripción") = descripcion
            filas(0)("Existencias") = CInt(filas(0)("Existencias")) + cantidad
        Else
            inventario.Rows.Add(producto, descripcion, cantidad)
        End If
        ActualizarTotal() : LimpiarInventario()
    End Sub

    Private Sub EliminarInventario(sender As Object, e As EventArgs)
        If dgvInventario.CurrentRow Is Nothing Then Return
        Dim producto = dgvInventario.CurrentRow.Cells("Producto").Value.ToString()
        Dim filas = inventario.Select("Producto = '" & producto.Replace("'", "''") & "'")
        If filas.Length > 0 Then inventario.Rows.Remove(filas(0))
        ActualizarTotal()
    End Sub

    Private Sub RegistrarEntrega(sender As Object, e As EventArgs)
        Dim producto = txtBuscar.Text.Trim()
        Dim cantidad As Integer
        If txtPersona.Text.Trim() = "" OrElse txtArea.Text.Trim() = "" OrElse producto = "" OrElse Not Integer.TryParse(txtCantidad.Text, cantidad) OrElse cantidad <= 0 Then
            MessageBox.Show("Complete persona, área, producto y cantidad.", "Datos inválidos", MessageBoxButtons.OK, MessageBoxIcon.Warning) : Return
        End If
        Dim filas = inventario.Select("Producto = '" & producto.Replace("'", "''") & "'")
        If filas.Length = 0 Then MessageBox.Show("El producto no existe en el inventario.") : Return
        Dim existencia = CInt(filas(0)("Existencias"))
        If cantidad > existencia Then MessageBox.Show("No hay existencias suficientes. Disponible: " & existencia) : Return
        filas(0)("Existencias") = existencia - cantidad
        entregas.Rows.Add(dtpFecha.Value.Date, txtPersona.Text.Trim(), txtArea.Text.Trim(), producto, cantidad)
        ActualizarTotal()
        txtCantidad.Clear()
        MessageBox.Show("Entrega registrada y existencia descontada correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information)
    End Sub

    Private Sub ActualizarTotal()
        Dim total As Integer = 0
        For Each r As DataRow In inventario.Rows : total += CInt(r("Existencias")) : Next
        lblTotal.Text = "Total de unidades en existencia: " & total
    End Sub

    Private Sub MostrarResumen(sender As Object, e As EventArgs)
        Dim resumen As String = "ENTREGAS REGISTRADAS" & Environment.NewLine & Environment.NewLine
        For Each r As DataRow In entregas.Rows
            resumen &= CDate(r("Fecha")).ToString("dd/MM/yyyy") & " | " & r("Persona") & " | Área: " & r("Área") & " | " & r("Producto") & " | Cantidad: " & r("Cantidad") & Environment.NewLine
        Next
        If entregas.Rows.Count = 0 Then resumen &= "No hay entregas registradas."
        MessageBox.Show(resumen, "Resumen", MessageBoxButtons.OK, MessageBoxIcon.Information)
    End Sub

    Private Sub LimpiarInventario()
        txtProducto.Clear() : txtDescripcion.Clear() : txtExistencia.Clear()
    End Sub
End Class

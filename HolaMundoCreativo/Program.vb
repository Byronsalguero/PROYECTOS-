Imports System

Module Program
    Sub Main()
        Console.Title = "Hola Mundo Creativo"

        Console.ForegroundColor = ConsoleColor.Cyan
        Console.WriteLine("========================================")
        Console.WriteLine("        HOLA MUNDO CREATIVO")
        Console.WriteLine("========================================")
        Console.ResetColor()

        Console.ForegroundColor = ConsoleColor.Green
        Console.WriteLine()
        Console.WriteLine("¡Hola Mundo! Bienvenido a Visual Basic.")
        Console.WriteLine("Este es mi primer programa creativo.")
        Console.WriteLine()
        Console.WriteLine("Presiona una tecla para salir...")
        Console.ResetColor()

        Console.ReadKey()
    End Sub
End Module

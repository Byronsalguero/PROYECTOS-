Imports System

Module Program
    Sub Main()
        Console.Title = "Calculadora Visual Basic"
        Dim opcion As Integer

        Do
            Console.Clear()
            Console.WriteLine("========================================")
            Console.WriteLine("       CALCULADORA EN VISUAL BASIC")
            Console.WriteLine("========================================")
            Console.WriteLine("1. Sumar")
            Console.WriteLine("2. Restar")
            Console.WriteLine("3. Multiplicar")
            Console.WriteLine("4. Dividir")
            Console.WriteLine("5. Exponente")
            Console.WriteLine("6. Borrar")
            Console.WriteLine("7. Salir")
            Console.WriteLine("========================================")
            Console.Write("Seleccione una opción: ")

            If Not Integer.TryParse(Console.ReadLine(), opcion) Then
                Console.WriteLine("Opción no válida.")
                Console.ReadKey()
                Continue Do
            End If

            Select Case opcion
                Case 1 To 5
                    Dim numero1 As Double
                    Dim numero2 As Double

                    Console.Write("Ingrese el primer número: ")
                    If Not Double.TryParse(Console.ReadLine(), numero1) Then
                        Console.WriteLine("Número no válido.")
                        Console.ReadKey()
                        Continue Do
                    End If

                    Console.Write("Ingrese el segundo número: ")
                    If Not Double.TryParse(Console.ReadLine(), numero2) Then
                        Console.WriteLine("Número no válido.")
                        Console.ReadKey()
                        Continue Do
                    End If

                    Select Case opcion
                        Case 1
                            Console.WriteLine($"Resultado: {numero1 + numero2}")
                        Case 2
                            Console.WriteLine($"Resultado: {numero1 - numero2}")
                        Case 3
                            Console.WriteLine($"Resultado: {numero1 * numero2}")
                        Case 4
                            If numero2 = 0 Then
                                Console.WriteLine("Error: no se puede dividir entre cero.")
                            Else
                                Console.WriteLine($"Resultado: {numero1 / numero2}")
                            End If
                        Case 5
                            Console.WriteLine($"Resultado: {Math.Pow(numero1, numero2)}")
                    End Select

                    Console.WriteLine("Presione una tecla para continuar...")
                    Console.ReadKey()

                Case 6
                    Console.Clear()
                    Console.WriteLine("Datos borrados. La calculadora ha sido reiniciada.")
                    Console.ReadKey()

                Case 7
                    Console.WriteLine("Gracias por usar la calculadora.")
                    Exit Do

                Case Else
                    Console.WriteLine("Opción no válida. Seleccione del 1 al 7.")
                    Console.ReadKey()
            End Select
        Loop
    End Sub
End Module

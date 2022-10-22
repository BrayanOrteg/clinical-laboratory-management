# clinical-laboratory-management
El timer saca cada minuto de las dos unidades ya que las tomamos como independientes cada una
El timer funciona despues de haber añadido a alguien
El deshacer una accion no devuelve a la fila los que fuero sacados por la maquina pero si baja la prioridad de la fila 
Cuando se hace un deshacer de alguien sacado por la maquina, es necesario volver a llamar al deshacer hasta que llegue a una acción realizada por el usuario

resource "aws_instance" "default" {
  ami                  = "ami-086cae3329a3f7d75"
  instance_type        = "t2.micro"
  subnet_id            = aws_subnet.public[0].id
  key_name             = aws_key_pair.deployer.key_name
  vpc_security_group_ids = [aws_security_group.ec2.id]
  iam_instance_profile = aws_iam_instance_profile.ec2.name
}

resource "tls_private_key" "default" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "aws_key_pair" "deployer" {
  key_name   = var.ec2_key_name
  public_key = tls_private_key.default.public_key_openssh
}

resource "local_file" "private_key" {
  content  = tls_private_key.default.private_key_pem
  filename = "${path.module}/handtris.pem"

  provisioner "local-exec" {
    command = "chmod 400 ${path.module}/handtris.pem"
  }
}

resource "aws_iam_instance_profile" "ec2" {
  name = "${var.project_name}-ec2-profile"
  role = "HandTris_EC2"
}

resource "aws_security_group" "ec2" {
  name   = "${var.project_name}-ec2-sg"
  vpc_id = aws_vpc.main.id

  ingress = [
    {
      cidr_blocks = ["0.0.0.0/0",]
      description = ""
      from_port   = 22
      ipv6_cidr_blocks = []
      prefix_list_ids = []
      protocol    = "tcp"
      security_groups = []
      self        = false
      to_port     = 22
    },
    {
      cidr_blocks = []
      description = ""
      from_port   = var.app_port
      ipv6_cidr_blocks = []
      prefix_list_ids = []
      protocol    = "tcp"
      security_groups = [aws_security_group.alb.id]
      self        = false
      to_port     = var.app_port
    }
  ]

  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

output "instance_public_ip" {
  value = aws_instance.default.public_ip
}